package org.ucb.appp1.feature.hub.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.ucb.appp1.exchange.presentation.screen.ExchangeScreen
import org.ucb.appp1.feature.announcements.presentation.screen.AnnouncementsScreen
import org.ucb.appp1.feature.localization.presentation.screen.LocalizationScreen
import org.ucb.appp1.feature.logs.presentation.screen.LogsScreen
import org.ucb.appp1.feature.pushnotifications.presentation.screen.PushScreen
import org.ucb.appp1.feature.remoteconfig.presentation.screen.RemoteConfigScreen

private val tabs = listOf("Dólar", "Config", "Avisos", "Push", "Idiomas", "gRPC")

/** Pantalla con una pestaña por cada feature de Firebase / Localise. */
@Composable
fun FeaturesHubScreen() {
    var selected by rememberSaveable { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                tabs.forEachIndexed { index, title ->
                    NavigationBarItem(
                        selected = selected == index,
                        onClick = { selected = index },
                        icon = { Text("•") },
                        label = { Text(title) }
                    )
                }
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (selected) {
                0 -> ExchangeScreen()            // Realtime Database
                1 -> RemoteConfigScreen()        // Remote Config
                2 -> AnnouncementsScreen()       // Firestore en tiempo real
                3 -> PushScreen()                // FCM: token, lista de tokens, deleteToken
                4 -> LocalizationScreen()        // Localise.biz
                else -> LogsScreen()             // gRPC (Wire)
            }
        }
    }
}
