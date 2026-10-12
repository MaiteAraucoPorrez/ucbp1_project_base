package org.ucb.appp1.feature.remoteconfig.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import org.ucb.appp1.feature.remoteconfig.presentation.viewmodel.RemoteConfigEvents
import org.ucb.appp1.feature.remoteconfig.presentation.viewmodel.RemoteConfigViewModel

@Composable
fun RemoteConfigScreen(
    modifier: Modifier = Modifier,
    viewModel: RemoteConfigViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val uriHandler = LocalUriHandler.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Remote Config", style = MaterialTheme.typography.headlineSmall)

        if (state.isLoading) CircularProgressIndicator()

        state.errorMessage?.let {
            Text(it, color = MaterialTheme.colorScheme.error)
        }

        state.config?.let { config ->
            if (!config.fromServer) {
                Text(
                    "Sin conexión con Firebase: se muestran los valores por defecto o guardados.",
                    color = MaterialTheme.colorScheme.error
                )
            }

            // Flag de actualización (pizarra: "Flag actualizaciones")
            if (config.updateRequired) {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("Hay una versión nueva de la app", style = MaterialTheme.typography.titleMedium)
                        Button(onClick = { uriHandler.openUri(config.storeUrl) }) {
                            Text("Actualizar en la tienda")
                        }
                    }
                }
            }

            Text("Teléfono de soporte: ${config.supportPhone}")
            Text(
                text = "Página web: ${config.websiteUrl}",
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable { uriHandler.openUri(config.websiteUrl) }
            )
            Text("Pasarela de pago activa: ${config.paymentProvider}")

            Text("Onboarding", style = MaterialTheme.typography.titleMedium)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("es", "en", "fr").forEach { lang ->
                    if (state.language == lang) {
                        Button(onClick = { viewModel.emitEvent(RemoteConfigEvents.OnLanguageSelected(lang)) }) {
                            Text(lang.uppercase())
                        }
                    } else {
                        OutlinedButton(onClick = { viewModel.emitEvent(RemoteConfigEvents.OnLanguageSelected(lang)) }) {
                            Text(lang.uppercase())
                        }
                    }
                }
            }

            config.onboarding.forEach { item ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(item.title.resolve(state.language), style = MaterialTheme.typography.titleSmall)
                        Text(item.description.resolve(state.language))
                        item.url?.let { url ->
                            Text(
                                text = url,
                                color = MaterialTheme.colorScheme.primary,
                                textDecoration = TextDecoration.Underline,
                                modifier = Modifier.clickable { uriHandler.openUri(url) }
                            )
                        }
                    }
                }
            }
        }

        Button(onClick = { viewModel.emitEvent(RemoteConfigEvents.OnRefresh) }) {
            Text("Actualizar valores")
        }
    }
}
