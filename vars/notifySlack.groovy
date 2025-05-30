def call(String type, String message) {
    def notifier = new org.myorg.utils.SlackNotifier(this)
    switch(type) {
        case 'success':
            notifier.success(message)
            break
        case 'failure':
            notifier.failure(message)
            break
        case 'info':
        default:
            notifier.info(message)
    }
}
