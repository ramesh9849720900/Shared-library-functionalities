package org.myorg.deploy

class KubernetesDeployer implements Serializable {
    def script
    def logger

    KubernetesDeployer(script) {
        this.script = script
        this.logger = script.logger  // <- pull logger from pipeline context
    }

    def deploy(Map config) {
        try {
            logger.logInfo("Deploying to Kubernetes: ${config.clusterName}")
            script.sh "kubectl config use-context ${config.context}"
            script.sh "kubectl apply -f ${config.manifestPath}"
        } catch (e) {
            logger.logError("Kubernetes deployment failed: ${e.message}")
            throw e
        }
    }
}
