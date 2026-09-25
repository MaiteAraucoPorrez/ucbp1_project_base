package org.ucb.appp1.feature.login.presentation

sealed interface LoginIntent {
    data class OnEmailChanged(val value: String) : LoginIntent
    data class OnPasswordChanged(val value: String) : LoginIntent
    data object OnTogglePasswordVisibility : LoginIntent
    data object OnLoginClicked : LoginIntent
    data object OnGoToSignUpClicked : LoginIntent
}
