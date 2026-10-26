package org.ucb.appp1.feature.login.domain.usecase

import org.ucb.appp1.core.domain.model.UserModel
import org.ucb.appp1.core.domain.repository.SessionRepository
import org.ucb.appp1.core.domain.vo.Email
import org.ucb.appp1.core.domain.vo.Password
import org.ucb.appp1.feature.login.domain.repository.AuthRepository

class LoginUseCase(
    private val repository: AuthRepository,
    private val session: SessionRepository
) {
    suspend operator fun invoke(email: Email, password: Password): Result<UserModel> = when {
        !email.isValid() -> Result.failure(IllegalArgumentException("Correo inválido"))
        !password.isValid() -> Result.failure(
            IllegalArgumentException("La contraseña debe tener ${Password.MIN_LENGTH}+ caracteres")
        )
        else -> repository.login(email, password).onSuccess { session.setCurrentUser(it) }
    }
}
