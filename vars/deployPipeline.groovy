
@Library('my-shared-lib') _

import org.myorg.config.ConfigLoader
import org.myorg.deploy.*
import org.myorg.utils.RetryHelper
import static logger.*
import org.myorg.utils.Logger

def call(Map params) {
    node {
        def config

        stage('Load Config') {
            try {
                config = ConfigLoader.load(params.team)
                logInfo("Config loaded for ${params.team}")
            } 
            catch (e) {
                logError("Kuberenetes deployment failed")
                error("Stopping pipeline due to config error.")
            }
        }

        stage('Deploy') {
            RetryHelper.retry(3) {
                if (params.deploymentType == 'kubernetes') {
                    new KubernetesDeployer().deploy(config)
                } 
                else {
                    error("Unsupported deployment type: ${params.deploymentType}")
                }
            }
        }
    }
}
