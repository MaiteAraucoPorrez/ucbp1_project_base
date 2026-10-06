package org.ucb.appp1.exchangerate.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ucb.appp1.exchangerate.domain.model.ExchangeRateModel
import org.ucb.appp1.exchangerate.domain.repository.ExchangeRateRepository

class ExchangeRateViewModel(
    val repository: ExchangeRateRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ExchangeRateState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<ExchangeRateEffect>()
    val effect = _effect.asSharedFlow()

    init {
        // Al abrir la pantalla se muestran los registros guardados en Room.
        viewModelScope.launch {
            val list = repository.getList()
            _state.update { it.copy(list = list) }
        }
    }

    fun emitEvent(event: ExchangeRateEvent) = viewModelScope.launch {
        when (event) {
            ExchangeRateEvent.OnAddRecord -> {
                repository.insert(ExchangeRateModel(official = "12.05", parallel = "12.07"))
                val list = repository.getList()
                _state.update { it.copy(list = list) }
            }
        }
    }
}
