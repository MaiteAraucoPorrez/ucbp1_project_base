package org.ucb.appp1.feature.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ucb.appp1.core.domain.vo.Email
import org.ucb.appp1.core.domain.vo.Password
import org.ucb.appp1.feature.login.domain.usecase.LoginUseCase

class LoginViewModel(
    private val login: LoginUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    private val _effect = Channel<LoginEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    fun onIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.OnEmailChanged ->
                _state.update { it.copy(email = intent.value, errorMessage = null) }

            is LoginIntent.OnPasswordChanged ->
                _state.update { it.copy(password = intent.value, errorMessage = null) }

            LoginIntent.OnTogglePasswordVisibility ->
                _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }

            LoginIntent.OnLoginClicked -> onLoginClicked()

            LoginIntent.OnGoToSignUpClicked -> sendEffect(LoginEffect.NavigateToSignUp)
        }
    }

    private fun onLoginClicked() {
        val current = _state.value
        if (!current.isLoginEnabled) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            login(Email(current.email), Password(current.password))
                .onSuccess {
                    _state.update { it.copy(isLoading = false) }
                    sendEffect(LoginEffect.NavigateToHome)
                }
                .onFailure { error ->
                    val message = error.message ?: "Usuario o contraseña incorrectos"
                    _state.update { it.copy(isLoading = false, errorMessage = message) }
                    sendEffect(LoginEffect.ShowError(message))
                }
        }
    }

    private fun sendEffect(effect: LoginEffect) {
        viewModelScope.launch { _effect.send(effect) }
    }
}
