package org.ucb.appp1.feature.movielist.presentation

sealed interface MovieListIntent {
    data object LoadMovies : MovieListIntent
    data class OnSearchQueryChanged(val query: String) : MovieListIntent
    data class OnMovieClicked(val movieId: String) : MovieListIntent
    data class OnFavoriteToggled(val movieId: String) : MovieListIntent
    data object OnRetryClicked : MovieListIntent
}
