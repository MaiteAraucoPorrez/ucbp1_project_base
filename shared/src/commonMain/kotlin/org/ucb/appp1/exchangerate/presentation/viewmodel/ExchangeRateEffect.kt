package org.ucb.appp1.exchangerate.presentation.viewmodel

sealed interface ExchangeRateEffect {
    data class ShowToast(val message: String) : ExchangeRateEffect
}
