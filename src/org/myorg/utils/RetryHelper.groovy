package org.myorg.utils

class RetryHelper {
    static void retry(int times, Closure action) {
        int attempt = 0
        while (attempt < times) {
            try {
                action.call()
                return
            } catch (e) {
                attempt++
                if (attempt == times) throw e
                sleep 2
            }
        }
    }
}
