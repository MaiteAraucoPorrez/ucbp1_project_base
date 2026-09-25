package org.ucb.appp1.feature.moviedetail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ucb.appp1.feature.moviedetail.domain.usecase.GetMovieDetailUseCase
import org.ucb.appp1.feature.movielist.domain.usecase.ToggleFavoriteUseCase

class MovieDetailViewModel(
    private val getMovieDetail: GetMovieDetailUseCase,
    private val toggleFavorite: ToggleFavoriteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(MovieDetailState())
    val state: StateFlow<MovieDetailState> = _state.asStateFlow()

    fun loadMovie(movieId: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            val movie = getMovieDetail(movieId)
            if (movie != null) {
                _state.update { it.copy(isLoading = false, movie = movie) }
            } else {
                _state.update { it.copy(isLoading = false, errorMessage = "Película no encontrada") }
            }
        }
    }

    fun onFavoriteClicked() {
        val movieId = _state.value.movie?.id ?: return
        viewModelScope.launch {
            toggleFavorite(movieId)
            loadMovie(movieId)
        }
    }
}
