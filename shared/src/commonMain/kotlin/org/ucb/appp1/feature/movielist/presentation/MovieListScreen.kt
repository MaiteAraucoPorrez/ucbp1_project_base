package org.ucb.appp1.feature.movielist.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.viewmodel.koinViewModel
import org.ucb.appp1.feature.movielist.domain.model.Movie

@Composable
fun MovieListScreen(
    onNavigateToDetail: (String) -> Unit,
    viewModel: MovieListViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is MovieListEffect.NavigateToDetail -> onNavigateToDetail(effect.movieId)
                is MovieListEffect.ShowError -> snackbarHostState.showSnackbar(effect.message)
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = { TopAppBar(title = { Text("Películas Populares") }) }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            OutlinedTextField(
                value = state.searchQuery,
                onValueChange = { viewModel.onIntent(MovieListIntent.OnSearchQueryChanged(it)) },
                label = { Text("Buscar películas...") },
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )

            when {
                state.isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }

                state.errorMessage != null -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(state.errorMessage.orEmpty())
                        Button(onClick = { viewModel.onIntent(MovieListIntent.OnRetryClicked) }) {
                            Text("Reintentar")
                        }
                    }
                }

                else -> LazyColumn {
                    items(state.movies, key = { it.id }) { movie ->
                        MovieRow(
                            movie = movie,
                            onClick = { viewModel.onIntent(MovieListIntent.OnMovieClicked(movie.id)) },
                            onFavoriteClick = { viewModel.onIntent(MovieListIntent.OnFavoriteToggled(movie.id)) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MovieRow(movie: Movie, onClick: () -> Unit, onFavoriteClick: () -> Unit) {
    ListItem(
        headlineContent = { Text(movie.title) },
        supportingContent = { Text(movie.genres.joinToString(", ")) },
        trailingContent = {
            TextButton(onClick = onFavoriteClick) {
                Text(if (movie.isFavorite) "★" else "☆")
            }
        },
        modifier = Modifier.clickable(onClick = onClick)
    )
}
