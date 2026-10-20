package org.ucb.appp1.scheduler

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.ucb.appp1.feature.remoteconfig.domain.usecase.GetAppConfigUseCase

/**
 * El Worker es el contenedor del trabajo. doWork() se ejecuta en segundo plano.
 *
 * Aquí el trabajo es refrescar Remote Config (sincronización periódica con el servidor).
 * Puedes reemplazarlo por cualquier otro caso de uso: solo cambia el "inject" y la llamada.
 */
class LogUploadWorker(
    appContext: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(appContext, workerParameters), KoinComponent {

    private val getAppConfig: GetAppConfigUseCase by inject()

    override suspend fun doWork(): ListenableWorker.Result {
        Log.d(TAG, "Ejecutando tarea en segundo plano (intento ${runAttemptCount + 1})")

        return getAppConfig().fold(
            onSuccess = { config ->
                Log.d(
                    TAG,
                    "Tarea OK. Pasarela de pago: ${config.paymentProvider}, " +
                        "valores del servidor: ${config.fromServer}"
                )
                ListenableWorker.Result.success()
            },
            onFailure = { error ->
                Log.e(TAG, "La tarea falló: ${error.message}")
                // retry() = WorkManager lo vuelve a intentar más tarde; failure() = se descarta.
                if (runAttemptCount < MAX_ATTEMPTS) {
                    ListenableWorker.Result.retry()
                } else {
                    ListenableWorker.Result.failure()
                }
            }
        )
    }

    private companion object {
        const val TAG = "LogUploadWorker"
        const val MAX_ATTEMPTS = 3
    }
}
