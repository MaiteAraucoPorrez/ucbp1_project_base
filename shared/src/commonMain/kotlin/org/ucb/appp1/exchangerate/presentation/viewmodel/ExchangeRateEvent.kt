package org.ucb.appp1.exchangerate.presentation.viewmodel

sealed interface ExchangeRateEvent {
    object OnAddRecord : ExchangeRateEvent
}
