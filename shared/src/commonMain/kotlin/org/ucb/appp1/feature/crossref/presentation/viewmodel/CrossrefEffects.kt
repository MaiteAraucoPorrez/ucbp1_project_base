package org.ucb.appp1.feature.crossref.presentation.viewmodel

sealed interface CrossrefEffects {
    data class ShowToast(val message: String) : CrossrefEffects
    data class NavigateToItemDetail(val itemId: String) : CrossrefEffects
}
