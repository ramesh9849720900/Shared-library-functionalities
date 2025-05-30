package org.myorg.utils

class SlackNotifier implements Serializable {
    def script

    SlackNotifier(script) {
        this.script = script
    }

    void send(String message, String color = '#439FE0') {
        script.slackSend(
            channel: '#deployments',      // Or make it dynamic
            color: color,
            message: message
        )
    }

    void notifySuccess(String message) {
        send(":white_check_mark: ${message}", 'good')
    }

    void notifyFailure(String message) {
        send(":x: ${message}", 'danger')
    }
}
