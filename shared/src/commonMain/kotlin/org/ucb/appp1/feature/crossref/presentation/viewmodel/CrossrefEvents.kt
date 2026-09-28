package org.ucb.appp1.feature.crossref.presentation.viewmodel

import org.ucb.appp1.feature.crossref.domain.model.CrossrefModel

sealed interface CrossrefEvents {
    data object OnLoadItems : CrossrefEvents
    data class OnItemClicked(val item: CrossrefModel) : CrossrefEvents
}
