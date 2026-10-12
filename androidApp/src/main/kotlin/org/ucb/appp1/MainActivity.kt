package org.ucb.appp1

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat

class MainActivity : ComponentActivity() {

    // Pide el permiso de notificaciones (Android 13+).
    private val notificationPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        requestNotificationPermission()
        if (savedInstanceState == null) openLandingPage(intent)

        setContent {
            App()
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        openLandingPage(intent)
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            notificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    // Si la app estaba cerrada y el usuario tocó una notificación que trae "url",
    // FCM entrega los datos como extras del Intent: abrimos esa landing page.
    private fun openLandingPage(intent: Intent?) {
        val url = intent?.getStringExtra("url")
        if (!url.isNullOrBlank()) {
            intent?.removeExtra("url")
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}
