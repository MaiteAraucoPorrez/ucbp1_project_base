package org.ucb.appp1.feature.pushnotifications.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
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
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import org.ucb.appp1.feature.pushnotifications.domain.model.PushSegment
import org.ucb.appp1.feature.pushnotifications.presentation.viewmodel.PushEvents
import org.ucb.appp1.feature.pushnotifications.presentation.viewmodel.PushViewModel

@Composable
fun PushScreen(
    modifier: Modifier = Modifier,
    viewModel: PushViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Notificaciones push (FCM)", style = MaterialTheme.typography.headlineSmall)

        Text("1. Segmento de este dispositivo", style = MaterialTheme.typography.titleMedium)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            PushSegment.entries.forEach { segment ->
                if (state.segment == segment) {
                    Button(onClick = { viewModel.emitEvent(PushEvents.OnSegmentSelected(segment)) }) {
                        Text(segment.label)
                    }
                } else {
                    OutlinedButton(onClick = { viewModel.emitEvent(PushEvents.OnSegmentSelected(segment)) }) {
                        Text(segment.label)
                    }
                }
            }
        }

        Text("2. Token", style = MaterialTheme.typography.titleMedium)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(onClick = { viewModel.emitEvent(PushEvents.OnGetToken) }) { Text("Ver token") }
            Button(onClick = { viewModel.emitEvent(PushEvents.OnRegisterToken) }) { Text("Registrar") }
            OutlinedButton(onClick = { viewModel.emitEvent(PushEvents.OnDeleteToken) }) { Text("deleteToken") }
        }

        if (state.isLoading) CircularProgressIndicator()
        state.message?.let { Text(it, color = MaterialTheme.colorScheme.primary) }

        state.currentToken?.let { token ->
            Text("Mi token (mantén presionado para copiar):", style = MaterialTheme.typography.labelLarge)
            SelectionContainer { Text(token, style = MaterialTheme.typography.bodySmall) }
        }

        Text("3. Lista de tokens registrados (${state.tokens.size})", style = MaterialTheme.typography.titleMedium)
        state.tokens.forEach { device ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        "${device.platform} · ${device.segment} · ${device.appId}",
                        style = MaterialTheme.typography.labelLarge
                    )
                    Text(device.token.take(40) + "…", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}
