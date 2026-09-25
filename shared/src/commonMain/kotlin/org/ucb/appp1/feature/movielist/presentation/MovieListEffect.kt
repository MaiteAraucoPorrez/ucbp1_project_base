package org.ucb.appp1.feature.movielist.presentation

sealed interface MovieListEffect {
    data class NavigateToDetail(val movieId: String) : MovieListEffect
    data class ShowError(val message: String) : MovieListEffect
}
