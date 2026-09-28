package org.ucb.appp1.feature.catalog.presentation.viewmodel

import org.ucb.appp1.feature.catalog.domain.model.MovieModel

data class CatalogState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val movies: List<MovieModel> = emptyList()
)
