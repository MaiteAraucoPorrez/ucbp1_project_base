package org.ucb.appp1.feature.crossref.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ucb.appp1.feature.crossref.domain.usecase.GetCrossrefUseCase

class CrossrefViewModel(
    private val getCrossrefUseCase: GetCrossrefUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CrossrefState())
    val state = _state.asStateFlow()

    private val _effects = MutableSharedFlow<CrossrefEffects>()
    val effects = _effects.asSharedFlow()

    init {
        emitEvent(CrossrefEvents.OnLoadItems)
    }

    private fun emitEffect(effect: CrossrefEffects) {
        viewModelScope.launch { _effects.emit(effect) }
    }

    fun emitEvent(event: CrossrefEvents) {
        when (event) {
            is CrossrefEvents.OnLoadItems -> fetchItems()
            is CrossrefEvents.OnItemClicked ->
                emitEffect(CrossrefEffects.NavigateToItemDetail(event.item.doi))
        }
    }

    private fun fetchItems() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            getCrossrefUseCase().fold(
                onSuccess = { items ->
                    _state.update { it.copy(isLoading = false, items = items) }
                },
                onFailure = { e ->
                    val msg = e.message ?: "Error desconocido"
                    _state.update { it.copy(isLoading = false, errorMessage = msg) }
                    emitEffect(CrossrefEffects.ShowToast("Error al cargar los items: $msg"))
                }
            )
        }
    }
}
