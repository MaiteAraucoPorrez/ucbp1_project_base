package org.ucb.appp1

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import org.ucb.appp1.navigation.AppNavHost
import org.ucb.appp1.exchangerate.presentation.screen.ExchangeRateScreen
import org.ucb.appp1.exchange.presentation.screen.ExchangeScreen


@Composable
fun App() {
    MaterialTheme {
        ExchangeScreen()
        //ExchangeRateScreen()
        //AppNavHost()
    }
}
