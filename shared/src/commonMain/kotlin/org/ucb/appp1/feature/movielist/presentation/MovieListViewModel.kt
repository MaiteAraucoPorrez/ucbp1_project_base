package org.ucb.appp1.feature.movielist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ucb.appp1.feature.movielist.domain.usecase.GetMoviesUseCase
import org.ucb.appp1.feature.movielist.domain.usecase.ToggleFavoriteUseCase

class MovieListViewModel(
    private val getMovies: GetMoviesUseCase,
    private val toggleFavorite: ToggleFavoriteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(MovieListState())
    val state: StateFlow<MovieListState> = _state.asStateFlow()

    private val _effect = Channel<MovieListEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    init {
        onIntent(MovieListIntent.LoadMovies)
    }

    fun onIntent(intent: MovieListIntent) {
        when (intent) {
            MovieListIntent.LoadMovies -> loadMovies(_state.value.searchQuery)

            is MovieListIntent.OnSearchQueryChanged -> {
                _state.update { it.copy(searchQuery = intent.query) }
                loadMovies(intent.query)
            }

            is MovieListIntent.OnMovieClicked ->
                sendEffect(MovieListEffect.NavigateToDetail(intent.movieId))

            is MovieListIntent.OnFavoriteToggled -> onFavoriteToggled(intent.movieId)

            MovieListIntent.OnRetryClicked -> loadMovies(_state.value.searchQuery)
        }
    }

    private fun loadMovies(query: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            runCatching { getMovies(query) }
                .onSuccess { movies -> _state.update { it.copy(isLoading = false, movies = movies) } }
                .onFailure { error ->
                    val message = error.message ?: "No se pudo cargar la lista de películas"
                    _state.update { it.copy(isLoading = false, errorMessage = message) }
                    sendEffect(MovieListEffect.ShowError(message))
                }
        }
    }

    private fun onFavoriteToggled(movieId: String) {
        viewModelScope.launch {
            toggleFavorite(movieId)
            loadMovies(_state.value.searchQuery)
        }
    }

    private fun sendEffect(effect: MovieListEffect) {
        viewModelScope.launch { _effect.send(effect) }
    }
}
