package org.ucb.appp1.feature.signup.domain.usecase

import org.ucb.appp1.core.domain.model.UserModel
import org.ucb.appp1.core.domain.repository.SessionRepository
import org.ucb.appp1.core.domain.vo.Email
import org.ucb.appp1.core.domain.vo.Password
import org.ucb.appp1.feature.signup.domain.repository.RegisterRepository

class RegisterUseCase(
    private val repository: RegisterRepository,
    private val session: SessionRepository
) {
    suspend operator fun invoke(fullName: String, emailRaw: String, password: String): Result<UserModel> {
        val email = Email(emailRaw)
        val passwordVo = Password(password)

        return when {
            fullName.isBlank() -> Result.failure(IllegalArgumentException("El nombre es obligatorio"))
            !email.isValid() -> Result.failure(IllegalArgumentException("Correo inválido"))
            !passwordVo.isValid() -> Result.failure(
                IllegalArgumentException("La contraseña debe tener ${Password.MIN_LENGTH}+ caracteres")
            )
            else -> repository.register(fullName, email, password).onSuccess { session.setCurrentUser(it) }
        }
    }
}
