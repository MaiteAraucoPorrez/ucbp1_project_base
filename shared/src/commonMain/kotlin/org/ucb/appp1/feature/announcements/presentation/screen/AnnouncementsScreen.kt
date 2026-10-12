package org.ucb.appp1.feature.announcements.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import org.ucb.appp1.feature.announcements.presentation.viewmodel.AnnouncementsEvents
import org.ucb.appp1.feature.announcements.presentation.viewmodel.AnnouncementsViewModel

@Composable
fun AnnouncementsScreen(
    modifier: Modifier = Modifier,
    viewModel: AnnouncementsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    var title by rememberSaveable { mutableStateOf("") }
    var message by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Avisos (Firestore en tiempo real)", style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Título") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = message,
            onValueChange = { message = it },
            label = { Text("Mensaje") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = {
            viewModel.emitEvent(AnnouncementsEvents.OnAdd(title, message))
            if (title.isNotBlank()) {
                title = ""
                message = ""
            }
        }) {
            Text("Publicar aviso")
        }

        state.errorMessage?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        if (state.isLoading) CircularProgressIndicator()

        if (!state.isLoading && state.announcements.isEmpty()) {
            Text("No hay avisos todavía. Crea uno aquí o desde la consola de Firebase.")
        }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(state.announcements, key = { it.id }) { announcement ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(announcement.title, style = MaterialTheme.typography.titleMedium)
                            if (announcement.message.isNotBlank()) Text(announcement.message)
                        }
                        TextButton(onClick = {
                            viewModel.emitEvent(AnnouncementsEvents.OnDelete(announcement.id))
                        }) {
                            Text("Eliminar")
                        }
                    }
                }
            }
        }
    }
}
