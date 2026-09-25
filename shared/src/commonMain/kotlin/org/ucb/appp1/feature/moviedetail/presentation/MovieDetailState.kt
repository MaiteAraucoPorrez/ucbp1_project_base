package org.ucb.appp1.feature.moviedetail.presentation

import org.ucb.appp1.feature.movielist.domain.model.Movie

/**
 * MVVM (como Registro): sin Intent/Effect. El ViewModel expone
 * funciones directas y este State refleja lo que la vista pintará.
 */
data class MovieDetailState(
    val isLoading: Boolean = false,
    val movie: Movie? = null,
    val errorMessage: String? = null
)
