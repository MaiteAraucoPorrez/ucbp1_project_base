package org.ucb.appp1

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import org.ucb.appp1.feature.hub.presentation.screen.FeaturesHubScreen

@Composable
fun App() {
    MaterialTheme {
        FeaturesHubScreen()
        // AppNavHost()   // navegación original de la app (login, películas, etc.)
    }
}
