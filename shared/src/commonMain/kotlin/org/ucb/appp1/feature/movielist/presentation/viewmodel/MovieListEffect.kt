package org.ucb.appp1.feature.movielist.presentation.viewmodel

import org.ucb.appp1.feature.movielist.domain.vo.MovieId

sealed interface MovieListEffect {
    data class NavigateToDetail(val movieId: MovieId) : MovieListEffect
    data class ShowError(val message: String) : MovieListEffect
}
