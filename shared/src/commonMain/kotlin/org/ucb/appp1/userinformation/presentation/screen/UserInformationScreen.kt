package org.ucb.appp1.userinformation.presentation.screen

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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import org.ucb.appp1.userinformation.presentation.viewmodel.UserInformationEffects
import org.ucb.appp1.userinformation.presentation.viewmodel.UserInformationEvents
import org.ucb.appp1.userinformation.presentation.viewmodel.UserInformationViewModel

/**
 * Feature "UserInformation" (búsqueda de usuarios de GitHub, ktor_kmp.pdf).
 */
@Composable
fun UserInformationScreen(
    onNavigateBack: () -> Unit,
    viewModel: UserInformationViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is UserInformationEffects.NavigateBack -> onNavigateBack()
                is UserInformationEffects.ShowToast -> snackbarHostState.showSnackbar(effect.message)
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Buscar en GitHub") },
                navigationIcon = {
                    TextButton(onClick = { viewModel.emitEvent(UserInformationEvents.OnBackClicked) }) {
                        Text("← Volver")
                    }
                }
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
                value = state.username,
                onValueChange = { viewModel.emitEvent(UserInformationEvents.OnUsernameChanged(it)) },
                label = { Text("Usuario de GitHub") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { viewModel.emitEvent(UserInformationEvents.OnSearchClicked) },
                enabled = !state.isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Buscar Usuario")
            }

            Spacer(modifier = Modifier.height(24.dp))

            val userInfo = state.userInfo
            val errorMessage = state.errorMessage
            when {
                state.isLoading -> CircularProgressIndicator()
                userInfo != null -> Card(modifier = Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp)) {
                        Text(
                            "Email: ${userInfo.email.ifEmpty { "No especificado" }}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Compañía: ${userInfo.company.ifEmpty { "No especificado" }}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Avatar URL: ${userInfo.avatarUrl.ifEmpty { "No disponible" }}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
                errorMessage != null -> Text(
                    "Error: $errorMessage",
                    color = MaterialTheme.colorScheme.error
                )
                else -> Text("Ingresa un usuario y presiona Buscar")
            }
        }
    }
}
