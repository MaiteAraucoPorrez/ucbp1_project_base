package org.ucb.appp1.feature.pushnotifications.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ucb.appp1.feature.pushnotifications.domain.usecase.DeleteDeviceTokenUseCase
import org.ucb.appp1.feature.pushnotifications.domain.usecase.GetCurrentTokenUseCase
import org.ucb.appp1.feature.pushnotifications.domain.usecase.ObserveDeviceTokensUseCase
import org.ucb.appp1.feature.pushnotifications.domain.usecase.RegisterDeviceTokenUseCase

class PushViewModel(
    private val getCurrentToken: GetCurrentTokenUseCase,
    private val registerDeviceToken: RegisterDeviceTokenUseCase,
    private val deleteDeviceToken: DeleteDeviceTokenUseCase,
    private val observeDeviceTokens: ObserveDeviceTokensUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PushState())
    val state = _state.asStateFlow()

    init {
        // Lista de tokens en tiempo real
        observeDeviceTokens()
            .onEach { list -> _state.update { it.copy(tokens = list) } }
            .catch { e -> _state.update { it.copy(message = "Error al leer tokens: ${e.message}") } }
            .launchIn(viewModelScope)
    }

    fun emitEvent(event: PushEvents) {
        when (event) {
            PushEvents.OnGetToken -> fetchToken()
            PushEvents.OnRegisterToken -> register()
            PushEvents.OnDeleteToken -> delete()
            is PushEvents.OnSegmentSelected -> _state.update { it.copy(segment = event.segment) }
        }
    }

    private fun fetchToken() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, message = null) }
            getCurrentToken().fold(
                onSuccess = { token ->
                    _state.update { it.copy(isLoading = false, currentToken = token) }
                },
                onFailure = { e ->
                    _state.update { it.copy(isLoading = false, message = "No se pudo obtener el token: ${e.message}") }
                }
            )
        }
    }

    private fun register() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, message = null) }
            registerDeviceToken(_state.value.segment).fold(
                onSuccess = { device ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            currentToken = device.token,
                            message = "Token registrado en el segmento ${device.segment}"
                        )
                    }
                },
                onFailure = { e ->
                    _state.update { it.copy(isLoading = false, message = "No se pudo registrar: ${e.message}") }
                }
            )
        }
    }

    private fun delete() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, message = null) }
            deleteDeviceToken().fold(
                onSuccess = {
                    _state.update {
                        it.copy(isLoading = false, currentToken = null, message = "Token eliminado")
                    }
                },
                onFailure = { e ->
                    _state.update { it.copy(isLoading = false, message = "No se pudo eliminar: ${e.message}") }
                }
            )
        }
    }
}
