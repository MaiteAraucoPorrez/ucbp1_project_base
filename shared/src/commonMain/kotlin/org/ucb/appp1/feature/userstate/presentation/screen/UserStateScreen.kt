package org.ucb.appp1.feature.userstate.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.viewmodel.koinViewModel
import org.ucb.appp1.feature.userstate.presentation.viewmodel.UserStateEffect
import org.ucb.appp1.feature.userstate.presentation.viewmodel.UserStateIntent
import org.ucb.appp1.feature.userstate.presentation.viewmodel.UserStateViewModel

/**
 * VIEW del diagrama de Perfil (MVI): emite UserStateIntent y solo lee UserStateState.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun UserStateScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToUserSearch: () -> Unit,
    onNavigateToEditProfile: () -> Unit,
    viewModel: UserStateViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    // Se recarga cada vez que se vuelve a esta pantalla (ej. al regresar
    // de "Editar Perfil"), para reflejar cambios recientes.
    LaunchedEffect(Unit) {
        viewModel.onIntent(UserStateIntent.LoadProfile)
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                UserStateEffect.NavigateToLogin -> onNavigateToLogin()
                UserStateEffect.NavigateToUserSearch -> onNavigateToUserSearch()
                UserStateEffect.NavigateToEditProfile -> onNavigateToEditProfile()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi Perfil") },
                actions = {
                    TextButton(onClick = { viewModel.onIntent(UserStateIntent.OnEditProfileClicked) }) {
                        Text("Editar")
                    }
                }
            )
        }
    ) { padding ->
        if (state.isLoading) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(state.user?.fullName ?: "Invitado", style = MaterialTheme.typography.headlineSmall)
            Text(state.user?.email?.value ?: "", style = MaterialTheme.typography.bodyMedium)

            Spacer(Modifier.height(24.dp))
            Text("Mis Películas Favoritas", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))

            if (state.favoriteMovies.isEmpty()) {
                Text("Aún no marcaste ninguna película como favorita")
            } else {
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    state.favoriteMovies.forEach { movie ->
                        AssistChip(onClick = {}, label = { Text(movie.title) })
                    }
                }
            }

            Spacer(Modifier.height(32.dp))

            OutlinedButton(
                onClick = { viewModel.onIntent(UserStateIntent.OnSearchGithubClicked) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Buscar Usuario de GitHub")
            }

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = { viewModel.onIntent(UserStateIntent.OnLogoutClicked) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cerrar Sesión")
            }
        }
    }
}
