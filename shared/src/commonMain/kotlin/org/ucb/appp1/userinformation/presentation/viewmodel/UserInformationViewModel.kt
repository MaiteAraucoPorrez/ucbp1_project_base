package org.ucb.appp1.userinformation.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ucb.appp1.userinformation.domain.usecase.FindAliasUseCase

class UserInformationViewModel(
    private val findAliasUseCase: FindAliasUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UserInformationState())
    val state = _state.asStateFlow()

    private val _effects = MutableSharedFlow<UserInformationEffects>()
    val effects = _effects.asSharedFlow()

    private fun emitEffect(effect: UserInformationEffects) {
        viewModelScope.launch { _effects.emit(effect) }
    }

    fun emitEvent(event: UserInformationEvents) {
        when (event) {
            is UserInformationEvents.OnUsernameChanged ->
                _state.update { it.copy(username = event.username) }
            is UserInformationEvents.OnSearchClicked -> findUser()
            is UserInformationEvents.OnBackClicked -> emitEffect(UserInformationEffects.NavigateBack)
        }
    }

    private fun findUser() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null, userInfo = null) }

            findAliasUseCase(_state.value.username).fold(
                onSuccess = { userInfo ->
                    _state.update { it.copy(isLoading = false, userInfo = userInfo) }
                },
                onFailure = { e ->
                    val msg = e.message ?: "Error desconocido"
                    _state.update { it.copy(isLoading = false, errorMessage = msg) }
                    emitEffect(UserInformationEffects.ShowToast("Error al buscar el usuario: $msg"))
                }
            )
        }
    }
}
