@Library('my-shared-lib') _

import org.myorg.config.ConfigLoader
import org.myorg.deploy.*
import org.myorg.utils.*

def call(Map params) {
    logger.logInfo("Starting deployment...")
    def config
    def slack = new SlackNotifier(this)
    node {
        stage('Load Config') { 
            try {
                def configLoader = new ConfigLoader(this)
                config = configLoader.load("config/${params.team}-config.yaml")
                logger.logInfo("Config loaded for ${params.team}")
                logger.logInfo("Received config: ${config}")
            } catch (e) {
                logger.logError("Exception while loading config: ${e}")
                slack.notifyFailure("===XXX=== Failed to load config for ${params.team}")
                error("Stopping pipeline due to config error.")
            }

            if (!config?.env || !config?.namespace || !config?.manifestPath) {
                logger.logError("Config is missing required fields: ${config}")
                slack.notifyFailure("===XXX=== Config missing required fields: ${params.team}")
                error("Stopping pipeline due to config error.")
            }
        }

        stage('Deploy') {
            def retryHelper = new RetryHelper(this)
            try {
           // def deploymentConfig = config
                retryHelper.retry(3) {
                    if (params.deploymentType == 'kubernetes') {
                        new KubernetesDeployer(this).deploy(config)
                    } else {
                        error("Unsupported deployment type: ${params.deploymentType}")
                    }
                }
              slack.notifySuccess("===YYY=== Deployment to ${config.env} Succeeded for ${params.team}")
            } catch (e) {
                slack.notifyFailure("===XXX=== Deployment failed for ${params.team}: ${e.message}")
                error("Deployment Failed")
            }
        }
    }
}
