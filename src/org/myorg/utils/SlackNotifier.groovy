package org.myorg.utils

class SlackNotifier implements Serializable {
    def script

    SlackNotifier(script) {
        this.script = script
    }

    void send(String message, String color = '#439FE0') {
        script.slackSend(
            channel: '#ci-cd-alerts',      // Or make it dynamic
            color: '#439FE0',
            message: message
        )
    }

    void notifySuccess(String message) {
        script.slackSend(
        color: 'good',
        message: ":white_check_mark: ${message}"
        )
    }

    void notifyFailure(String message) {
        script.slackSend(
        color: 'danger',
        message: ":x: ${message}"
        )
    }
}
