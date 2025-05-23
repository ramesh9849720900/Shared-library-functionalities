/*def logInfo(msg) {
    echo "[INFO] ${msg}"
}

def logDebug(msg) {
    echo "[DEBUG] ${msg}"
}

def logError(msg) {
    echo "[ERROR] ${msg}"
}*/


// vars/logError.groovy
def call(String message) {
    echo "[ERROR] ${message}"
}
