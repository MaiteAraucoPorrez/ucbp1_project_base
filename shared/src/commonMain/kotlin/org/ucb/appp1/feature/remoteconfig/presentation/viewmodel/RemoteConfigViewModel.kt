package org.ucb.appp1.feature.remoteconfig.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ucb.appp1.feature.remoteconfig.domain.usecase.GetAppConfigUseCase

class RemoteConfigViewModel(
    private val getAppConfigUseCase: GetAppConfigUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(RemoteConfigState())
    val state = _state.asStateFlow()

    init {
        emitEvent(RemoteConfigEvents.OnLoad)
    }

    fun emitEvent(event: RemoteConfigEvents) {
        when (event) {
            RemoteConfigEvents.OnLoad -> loadConfig()
            RemoteConfigEvents.OnRefresh -> loadConfig()
            is RemoteConfigEvents.OnLanguageSelected ->
                _state.update { it.copy(language = event.language) }
        }
    }

    private fun loadConfig() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            getAppConfigUseCase().fold(
                onSuccess = { config ->
                    _state.update { it.copy(isLoading = false, config = config) }
                },
                onFailure = { e ->
                    _state.update {
                        it.copy(isLoading = false, errorMessage = e.message ?: "Error desconocido")
                    }
                }
            )
        }
    }
}
