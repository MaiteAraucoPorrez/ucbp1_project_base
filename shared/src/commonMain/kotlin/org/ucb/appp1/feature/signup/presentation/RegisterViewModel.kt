package org.ucb.appp1.feature.signup.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ucb.appp1.feature.signup.domain.usecase.RegisterUseCase

class RegisterViewModel(
    private val register: RegisterUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state.asStateFlow()

    // Funciones directas (Binds/Function del diagrama MVVM), no Intents.
    fun onFullNameChanged(value: String) = _state.update { it.copy(fullName = value, errorMessage = null) }
    fun onEmailChanged(value: String) = _state.update { it.copy(email = value, errorMessage = null) }
    fun onPasswordChanged(value: String) = _state.update { it.copy(password = value, errorMessage = null) }
    fun onConfirmPasswordChanged(value: String) = _state.update { it.copy(confirmPassword = value, errorMessage = null) }

    fun onRegisterClicked() {
        val current = _state.value
        if (current.password != current.confirmPassword) {
            _state.update { it.copy(errorMessage = "Las contraseñas no coinciden") }
            return
        }
        if (!current.isFormValid) {
            _state.update { it.copy(errorMessage = "Completa todos los campos") }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            register(current.fullName, current.email, current.password)
                .onSuccess {
                    _state.update { it.copy(isLoading = false, isRegistered = true) }
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(isLoading = false, errorMessage = error.message ?: "No se pudo crear la cuenta")
                    }
                }
        }
    }
}
