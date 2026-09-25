package org.ucb.appp1.feature.login.presentation

sealed interface LoginEffect {
    data object NavigateToHome : LoginEffect
    data object NavigateToSignUp : LoginEffect
    data class ShowError(val message: String) : LoginEffect
}
