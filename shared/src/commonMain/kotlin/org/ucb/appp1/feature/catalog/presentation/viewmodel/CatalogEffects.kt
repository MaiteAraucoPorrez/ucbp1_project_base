package org.ucb.appp1.feature.catalog.presentation.viewmodel

sealed interface CatalogEffects {
    data class ShowToast(val message: String) : CatalogEffects
    data class NavigateToMovieDetail(val movieId: String) : CatalogEffects
}