package org.ucb.appp1.scheduler

/**
 * iOS: por ahora no hace nada (WorkManager es solo de Android).
 * Existe para que el proyecto compile también en iOS.
 * En iOS esto se haría con BGTaskScheduler.
 */
actual class LogScheduler actual constructor(
    context: Any?
) {
    actual fun schedulePeriodicalUpload() {
        // pendiente en iOS
    }

    actual fun scheduleOneTimeUpload() {
        // pendiente en iOS
    }
}
