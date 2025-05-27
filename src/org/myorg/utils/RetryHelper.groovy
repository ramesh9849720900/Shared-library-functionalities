package org.myorg.utils

class RetryHelper implements Serializable {

    def script // pipeline context (e.g. `this` from Jenkinsfile)

    RetryHelper(script) {
        this.script = script
    }

    def retry(int retries, int waitTime = 5, Closure block) {
        int attempts = 0
        while (attempts < retries) {
            try {
                return block.call()
            } catch (Exception e) {
                attempts++
                if (attempts >= retries) {
                    throw e
                }
                script.echo "[RetryHelper] Attempt ${attempts} failed: ${e.message}, retrying after ${waitTime} seconds..."
                script.sleep waitTime
            }
        }
    }
}
