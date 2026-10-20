package org.ucb.appp1.scheduler

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

actual class LogScheduler actual constructor(
    context: Any?
) {
    private val androidContext = context as? Context
        ?: throw IllegalArgumentException("Context must be an instance of android.content.Context")

    private val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED) // solo corre si hay internet
        .build()

    actual fun schedulePeriodicalUpload() {
        // PeriodicWorkRequest: se repite cada INTERVAL_MINUTES (WorkManager exige mínimo 15).
        val request = PeriodicWorkRequest.Builder(
            LogUploadWorker::class.java,
            INTERVAL_MINUTES,
            TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(androidContext.applicationContext)
            .enqueueUniquePeriodicWork(
                LOG_WORKNAME,
                ExistingPeriodicWorkPolicy.KEEP, // si ya está programada, la deja como está
                request
            )
    }

    actual fun scheduleOneTimeUpload() {
        // OneTimeWorkRequest: se ejecuta una sola vez (ideal para probar).
        val request = OneTimeWorkRequestBuilder<LogUploadWorker>()
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(androidContext.applicationContext)
            .enqueueUniqueWork(
                LOG_WORKNAME_ONCE,
                ExistingWorkPolicy.REPLACE, // si ya hay una en cola, la reemplaza
                request
            )
    }

    private companion object {
        const val LOG_WORKNAME = "logUploadWork"
        const val LOG_WORKNAME_ONCE = "logUploadWorkOnce"
        const val INTERVAL_MINUTES = 15L
    }
}
