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

        // Load YAML from shared library resources
        def manifestContent = script.libraryResource(config.manifestPath)
        script.writeFile file: 'deployment.yaml', text: manifestContent

        // Apply it using kubectl
        script.sh "kubectl apply -f deployment.yaml"
    } catch (e) {
        logger.logError("Kubernetes deployment failed: ${e.message}")
        throw e
        }
    }
}
