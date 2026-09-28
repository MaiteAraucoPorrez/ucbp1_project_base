package org.ucb.appp1.feature.crossref.presentation.viewmodel

import org.ucb.appp1.feature.crossref.domain.model.CrossrefModel

data class CrossrefState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val items: List<CrossrefModel> = emptyList()
)
