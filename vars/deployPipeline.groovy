@Library('my-shared-lib') _

import org.myorg.config.ConfigLoader
import org.myorg.deploy.*
import org.myorg.utils.RetryHelper

def call(Map params) {
    logger.logInfo("Starting deployment...")
    def config
    node {
        stage('Load Config') { 
            try {
                def configLoader = new ConfigLoader(this)
                config = configLoader.load("config/${params.team}-config.yaml")
                logger.logInfo("Config loaded for ${params.team}")
                logger.logInfo("Received config: ${config}")
            } catch (e) {
                logger.logError("Exception while loading config: ${e}")
                error("Stopping pipeline due to config error.")
            }

            if (!config?.env || !config?.namespace || !config?.manifestPath) {
                logger.logError("Config is missing required fields: ${config}")
                error("Stopping pipeline due to config error.")
            }
        }

        stage('Deploy') {
            def retryHelper = new RetryHelper(this)
           // def deploymentConfig = config
            retryHelper.retry(3) {
                if (params.deploymentType == 'kubernetes') {
                    new KubernetesDeployer(this).deploy(config)
                } else {
                    error("Unsupported deployment type: ${params.deploymentType}")
                }
            }
        }
    }
}
