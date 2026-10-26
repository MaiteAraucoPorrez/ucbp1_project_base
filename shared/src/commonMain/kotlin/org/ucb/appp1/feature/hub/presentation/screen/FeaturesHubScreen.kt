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
import appp1.shared.generated.resources.Res
import appp1.shared.generated.resources.tab_announcements
import appp1.shared.generated.resources.tab_config
import appp1.shared.generated.resources.tab_dollar
import appp1.shared.generated.resources.tab_grpc
import appp1.shared.generated.resources.tab_languages
import appp1.shared.generated.resources.tab_push
import org.jetbrains.compose.resources.stringResource
import org.ucb.appp1.exchange.presentation.screen.ExchangeScreen
import org.ucb.appp1.feature.announcements.presentation.screen.AnnouncementsScreen
import org.ucb.appp1.feature.localization.presentation.screen.LocalizationScreen
import org.ucb.appp1.feature.logs.presentation.screen.LogsScreen
import org.ucb.appp1.feature.pushnotifications.presentation.screen.PushScreen
import org.ucb.appp1.feature.remoteconfig.presentation.screen.RemoteConfigScreen

// Los títulos vienen de Localise.biz (downloadLocoStrings), así cambian con el idioma del dispositivo.
private val tabs = listOf(
    Res.string.tab_dollar,
    Res.string.tab_config,
    Res.string.tab_announcements,
    Res.string.tab_push,
    Res.string.tab_languages,
    Res.string.tab_grpc
)

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
                        label = { Text(stringResource(title)) }
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
