package org.ucb.appp1.exchangerate.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import org.ucb.appp1.exchangerate.presentation.viewmodel.ExchangeRateEvent
import org.ucb.appp1.exchangerate.presentation.viewmodel.ExchangeRateViewModel

@Composable
fun ExchangeRateScreen(
    viewModel: ExchangeRateViewModel = koinViewModel()
) {
    val state = viewModel.state.collectAsState()
    Column(modifier = Modifier.statusBarsPadding().padding(16.dp)) {
        Button(onClick = {
            viewModel.emitEvent(ExchangeRateEvent.OnAddRecord)
        }) {
            Text("Add")
        }
        Text("Registros: ${state.value.list.size}")
        state.value.list.forEach {
            Text("Oficial: ${it.official} | Paralelo: ${it.parallel}")
        }
    }
}
