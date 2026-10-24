package org.ucb.appp1.feature.logs.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import org.ucb.appp1.feature.logs.presentation.viewmodel.LogsEvents
import org.ucb.appp1.feature.logs.presentation.viewmodel.LogsViewModel

@Composable
fun LogsScreen(
    modifier: Modifier = Modifier,
    viewModel: LogsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Logs por gRPC (Wire)", style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(
            value = state.serverUrl,
            onValueChange = { viewModel.emitEvent(LogsEvents.OnUrlChanged(it)) },
            label = { Text("Dirección del servidor") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            "Emulador: http://10.0.2.2:9090   ·   ngrok: https://xxxx.ngrok-free.dev",
            style = MaterialTheme.typography.bodySmall
        )

        Button(
            onClick = { viewModel.emitEvent(LogsEvents.OnSendTestLog) },
            enabled = !state.isLoading
        ) {
            Text("Enviar log de prueba")
        }

        if (state.isLoading) CircularProgressIndicator()

        state.message?.let {
            Text(
                text = it,
                color = if (state.isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
            )
        }
    }
}
