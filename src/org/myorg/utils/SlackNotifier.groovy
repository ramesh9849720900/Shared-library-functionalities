package org.myorg.utils

class SlackNotifier implements Serializable {
    def script

    SlackNotifier(script) {
        this.script = script
    }

    def send(String message, String color = '#36a64f') {
        script.slackSend(
            channel: '#your-channel',
            color: color,
            message: message
        )
    }

    def success(String msg) {
        send("✅ SUCCESS: ${msg}", 'good')
    }

    def failure(String msg) {
        send("❌ FAILURE: ${msg}", 'danger')
    }

    def info(String msg) {
        send("ℹ️ INFO: ${msg}", '#439FE0')
    }
}
