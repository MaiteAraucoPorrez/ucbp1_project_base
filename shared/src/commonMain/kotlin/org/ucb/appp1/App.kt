package org.ucb.appp1

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.ucb.appp1.navigation.AppNavHost
import org.ucb.appp1.exchangerate.presentation.screen.ExchangeRateScreen
import org.ucb.appp1.exchange.presentation.screen.ExchangeScreen
import org.ucb.appp1.feature.announcements.presentation.screen.AnnouncementsScreen
import org.ucb.appp1.feature.pushnotifications.presentation.screen.PushScreen
import org.ucb.appp1.feature.remoteconfig.presentation.screen.RemoteConfigScreen
import appp1.shared.generated.resources.Res
import appp1.shared.generated.resources.loc_title
import org.jetbrains.compose.resources.stringResource
import org.ucb.appp1.feature.localization.presentation.screen.LocalizationScreen


@Composable
fun App() {
    MaterialTheme {
        Column {
            Text(stringResource(Res.string.loc_title))
            LocalizationScreen()
        }
        //PushScreen()
        //AnnouncementsScreen()
        //RemoteConfigScreen()
        //ExchangeScreen()
        //ExchangeRateScreen()
        //AppNavHost()
    }
}
