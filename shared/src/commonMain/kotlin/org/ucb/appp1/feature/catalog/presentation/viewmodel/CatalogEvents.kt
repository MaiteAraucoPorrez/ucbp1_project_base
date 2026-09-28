package org.ucb.appp1.feature.catalog.presentation.viewmodel

import org.ucb.appp1.feature.catalog.domain.model.MovieModel

sealed interface CatalogEvents {
    data object OnLoadMovies : CatalogEvents
    data class OnMovieClicked(val movie: MovieModel) : CatalogEvents
}
