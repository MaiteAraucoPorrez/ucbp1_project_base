package org.ucb.appp1

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import org.ucb.appp1.navigation.AppNavHost
import org.ucb.appp1.exchangerate.presentation.screen.ExchangeRateScreen
import org.ucb.appp1.exchange.presentation.screen.ExchangeScreen
import org.ucb.appp1.feature.remoteconfig.presentation.screen.RemoteConfigScreen


@Composable
fun App() {
    MaterialTheme {
        RemoteConfigScreen()
        //ExchangeScreen()
        //ExchangeRateScreen()
        //AppNavHost()
    }
}
