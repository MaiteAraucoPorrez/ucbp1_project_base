package org.ucb.appp1

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

/**
 * Recibe los mensajes de FCM.
 *
 * - Notificación "normal" con la app abierta: se muestra con showNotification().
 * - Notificación "silenciosa": mensaje de datos con silent=true. No muestra nada, la app
 *   puede aprovecharlo para trabajar en segundo plano (por ejemplo refrescar Remote Config).
 * - Si el mensaje trae el dato "url", al tocar la notificación se abre esa landing page.
 */
class AppFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "Nuevo token FCM: $token")
        // Aquí se podría actualizar el token en Firestore si el usuario ya estaba registrado.
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val data = message.data

        if (data["silent"] == "true") {
            Log.d(TAG, "Notificación silenciosa recibida: $data")
            return
        }

        val title = message.notification?.title ?: data["title"] ?: getString(R.string.app_name)
        val body = message.notification?.body ?: data["body"] ?: ""
        showNotification(title, body, data["url"])
    }

    @SuppressLint("MissingPermission")
    private fun showNotification(title: String, body: String, url: String?) {
        // Android 13+ exige el permiso POST_NOTIFICATIONS.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        val intent = (
            if (!url.isNullOrBlank()) {
                Intent(Intent.ACTION_VIEW, Uri.parse(url))
            } else {
                Intent(this, MainActivity::class.java)
            }
            ).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(this, NotificationChannels.DEFAULT)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        NotificationManagerCompat.from(this).notify(System.currentTimeMillis().toInt(), notification)
    }

    private companion object {
        const val TAG = "FCM"
    }
}
