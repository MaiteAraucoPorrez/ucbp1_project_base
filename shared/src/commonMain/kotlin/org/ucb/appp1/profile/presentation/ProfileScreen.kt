package org.ucb.appp1.profile.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = koinInject()
) {
    val uiState by viewModel.uiState.collectAsState()
    var username by remember { mutableStateOf("octocat") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Perfil de GitHub",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Usuario de GitHub") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { viewModel.fetchUser(username) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Buscar Usuario")
        }

        Spacer(modifier = Modifier.height(24.dp))

        when (val state = uiState) {
            is ProfileUiState.Idle -> {
                Text("Ingresa un usuario y presiona Buscar")
            }
            is ProfileUiState.Loading -> {
                CircularProgressIndicator()
            }
            is ProfileUiState.Success -> {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Email: ${state.userInfo.email.ifEmpty { "No especificado" }}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Avatar URL: ${state.userInfo.avatarUrl.ifEmpty { "No disponible" }}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
            is ProfileUiState.Error -> {
                Text(
                    text = "Error: ${state.message}",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
