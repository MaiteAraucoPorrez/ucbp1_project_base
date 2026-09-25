package org.ucb.appp1

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import org.ucb.appp1.navigation.AppNavHost

@Composable
fun App() {
    MaterialTheme {
        AppNavHost()
    }
}
