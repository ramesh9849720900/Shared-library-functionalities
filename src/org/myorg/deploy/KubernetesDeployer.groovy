package org.myorg.deploy

import static logger.*

class KubernetesDeployer {
    def deploy(Map config) {
        try {
            logInfo("Deploying to Kubernetes: ${config.clusterName}")
            sh "kubectl config use-context ${config.context}"
            sh "kubectl apply -f ${config.manifest}"
        } catch (e) {
            logError("Kubernetes deployment failed: ${e.message}")
            throw e
        }
    }
}
