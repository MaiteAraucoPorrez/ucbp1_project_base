package org.ucb.appp1

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import org.ucb.appp1.di.initKoinAndroid
import org.ucb.appp1.scheduler.LogScheduler

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoinAndroid(this)
        createNotificationChannel()

        // Tarea en segundo plano (WorkManager): cada 15 minutos y solo con internet.
        val logScheduler = LogScheduler(this)
        logScheduler.schedulePeriodicalUpload()
        // Para probar sin esperar 15 minutos, usa en su lugar:
        //logScheduler.scheduleOneTimeUpload()
    }

    // Android 8+ necesita un canal para mostrar notificaciones.
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NotificationChannels.DEFAULT,
                "General",
                NotificationManager.IMPORTANCE_HIGH
            )
            getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        }
    }
}
