def call(String type, String message) {
    def notifier = new org.myorg.utils.SlackNotifier(this)
    switch(type) {
        case 'success':
            notifier.notifySuccess(message)
            break
        case 'failure':
            notifier.notifyFailure(message)
            break
        case 'info':
        default:
            notifier.send(message)
    }
}
