package org.ucb.appp1.feature.userstate.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import org.ucb.appp1.feature.userstate.presentation.viewmodel.UserStateEditViewModel

/**
 * Pantalla nueva (no estaba en las 5 originales): editar el nombre del
 * usuario ya logueado. MVVM, igual patrón que Registro/Movie Detail.
 */
@Composable
fun UserStateEditScreen(
    onNavigateBack: () -> Unit,
    viewModel: UserStateEditViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.isSaved) {
        if (state.isSaved) onNavigateBack()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar Perfil") },
                navigationIcon = {
                    TextButton(onClick = onNavigateBack) { Text("← Volver") }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = state.fullName,
                onValueChange = viewModel::onFullNameChanged,
                label = { Text("Nombre Completo") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            if (state.errorMessage != null) {
                Spacer(Modifier.height(8.dp))
                Text(state.errorMessage.orEmpty(), color = MaterialTheme.colorScheme.error)
            }

            Spacer(Modifier.height(24.dp))

            if (state.isLoading) {
                CircularProgressIndicator()
            } else {
                Button(
                    onClick = viewModel::onSaveClicked,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("GUARDAR CAMBIOS")
                }
            }
        }
    }
}
