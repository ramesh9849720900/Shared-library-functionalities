package org.myorg.utils

class SlackNotifier implements Serializable {
    def script
    SlackNotifier(script) {
        this.script = script
    }

    void send(String message, String color = '#439FE0') {
        def buildUser = script.currentBuild.getBuildCauses('hudson.model.Cause$UserIdCause')[0]?.userName ?: "Unknown"
        def timestamp = new Date().format("yyyy-MM-dd HH:mm:ss")
        def finalMessage = """\
*${message}*
_Triggered by_: ${buildUser}
_Time_: ${timestamp}
"""
        script.slackSend(
            channel: '#ci-cd-alerts',
            color: color,
            message: finalMessage,
            teamDomain: "DevOps_Engineers"
        )
    }

    void notifySuccess(String message) {
        script.slackSend(
            color: 'good',
            message: ":white_check_mark: ${message}",
            teamDomain: "DevOps_Engineers"
        )
    }

    void notifyFailure(String message) {
        def buildUser = script.currentBuild.getBuildCauses('hudson.model.Cause$UserIdCause')[0]?.userName ?: "Unknown"
        def timestamp = new Date().format("yyyy-MM-dd HH:mm:ss")

        script.echo("Triggered by: ${buildUser}")
        script.echo("Time: ${timestamp}")

        def finalMessage = """\
:x: ${message}
_Triggered by_: ${buildUser}
_Time_: ${timestamp}
"""

        script.slackSend(
            color: 'danger',
            message: finalMessage,
            teamDomain: "DevOps_Engineers"
        )
    }
}
