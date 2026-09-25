package org.ucb.appp1.feature.signup.presentation

/**
 * MVVM: a diferencia de Login (MVI), aquí no hay Intent ni Effect
 * separados; el ViewModel expone funciones directas y la navegación
 * se decide observando el flag `isRegistered` del propio State.
 */
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
