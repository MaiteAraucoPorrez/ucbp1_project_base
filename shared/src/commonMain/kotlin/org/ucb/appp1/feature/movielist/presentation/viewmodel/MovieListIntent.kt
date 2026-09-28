package org.ucb.appp1.feature.movielist.presentation.viewmodel

import org.ucb.appp1.feature.movielist.domain.vo.MovieId

sealed interface MovieListIntent {
    data object LoadMovies : MovieListIntent
    data class OnSearchQueryChanged(val query: String) : MovieListIntent
    data class OnMovieClicked(val movieId: MovieId) : MovieListIntent
    data class OnFavoriteToggled(val movieId: MovieId) : MovieListIntent
    data object OnRetryClicked : MovieListIntent
}
