package org.myorg.utils

class SlackNotifier implements Serializable {
    def script

    SlackNotifier(script) {
        this.script = script
    }

    void send(String message, String color = '#439FE0') {
         def buildUser = steps.currentBuild.getBuildCauses('hudson.model.Cause$UserIdCause')[0]?.userName ?: "Unknown"
         def timestamp = new Date().format("yyyy-MM-dd HH:mm:ss")
         def finalMessage = """\
*${message}*
_Triggered by_: ${buildUser}
_Time_: ${timestamp}
"""
        script.slackSend(
            channel: '#ci-cd-alerts',      // Or make it dynamic
            color: '#439FE0',
            message: message
            teamDomain: "DevOps_Engineers"
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
_Triggered by_: ${steps.currentBuild.getBuildCauses('hudson.model.Cause$UserIdCause')[0]?.userName ?: "Unknown"}
_Time_: ${new Date().format("yyyy-MM-dd HH:mm:ss")}
""",
        message: ":x: ${message}"
        teamDomain: "DevOps_Engineers"
        )
    }
}
