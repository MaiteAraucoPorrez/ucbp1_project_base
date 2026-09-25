package org.ucb.appp1.feature.movielist.presentation

import org.ucb.appp1.feature.movielist.domain.model.Movie

data class MovieListState(
    val isLoading: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val searchQuery: String = "",
    val errorMessage: String? = null
)
