package org.ucb.appp1.feature.signup.presentation.viewmodel

data class RegisterState(
    val fullName: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isRegistered: Boolean = false
) {
    val isFormValid: Boolean
        get() = fullName.isNotBlank() &&
            email.isNotBlank() &&
            password.isNotBlank() &&
            password == confirmPassword &&
            !isLoading
}
