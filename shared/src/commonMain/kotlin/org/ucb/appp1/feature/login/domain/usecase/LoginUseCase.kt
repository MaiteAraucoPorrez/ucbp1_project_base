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
    suspend operator fun invoke(email: Email, password: Password): Result<UserModel> {
        if (!email.isValid()) return Result.failure(IllegalArgumentException("Correo inválido"))
        if (!password.isValid()) return Result.failure(IllegalArgumentException("La contraseña debe tener 6+ caracteres"))
        return repository.login(email, password).onSuccess { session.setCurrentUser(it) }
    }
}
