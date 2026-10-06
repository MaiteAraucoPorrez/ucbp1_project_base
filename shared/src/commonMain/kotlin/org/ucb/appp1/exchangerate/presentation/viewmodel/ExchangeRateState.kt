package org.ucb.appp1.exchangerate.presentation.viewmodel

import org.ucb.appp1.exchangerate.domain.model.ExchangeRateModel

data class ExchangeRateState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val list: List<ExchangeRateModel> = emptyList()
)
