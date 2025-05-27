@Library('my-shared-lib') _

import org.myorg.config.ConfigLoader
import org.myorg.deploy.*
import org.myorg.utils.RetryHelper

def call(Map params = [:]) {
    node {
        stage('Load Config') {
            def configLoader = new ConfigLoader(this)
            def config = configLoader.load('config/teamA-config.yaml')
            echo "Config loaded: ${config}"
        }

        stage('Load Config') {
            try {
                config = ConfigLoader.load(params.team)
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
            RetryHelper.retry(3) {
                if (params.deploymentType == 'kubernetes') {
                    new KubernetesDeployer().deploy(config)
                } else {
                    error("Unsupported deployment type: ${params.deploymentType}")
                }
            }
        }
    }
}
