package org.ucb.appp1.userinformation.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel

/**
 * Feature "UserInformation" (búsqueda de usuarios de GitHub, ktor_kmp.pdf).
 * Antes vivía mal ubicado dentro del paquete `profile`; ahora usa el
 * mismo nombre de paquete que el PDF: org.ucb.appp1.userinformation.
 */
@Composable
fun UserInformationScreen(
    onNavigateBack: () -> Unit,
    viewModel: UserInformationViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var username by remember { mutableStateOf("octocat") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Buscar en GitHub") },
                navigationIcon = { TextButton(onClick = onNavigateBack) { Text("← Volver") } }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
                is UserInformationUiState.Idle -> Text("Ingresa un usuario y presiona Buscar")
                is UserInformationUiState.Loading -> CircularProgressIndicator()
                is UserInformationUiState.Success -> Card(modifier = Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp)) {
                        Text(
                            "Email: ${state.userInfo.email.ifEmpty { "No especificado" }}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Compañía: ${state.userInfo.company.ifEmpty { "No especificado" }}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Avatar URL: ${state.userInfo.avatarUrl.ifEmpty { "No disponible" }}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
                is UserInformationUiState.Error -> Text(
                    "Error: ${state.message}",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
