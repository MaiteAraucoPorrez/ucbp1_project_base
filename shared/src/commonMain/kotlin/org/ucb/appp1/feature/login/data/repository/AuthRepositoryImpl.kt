package org.ucb.appp1.feature.login.data.repository

import org.ucb.appp1.core.data.InMemoryAuthStore
import org.ucb.appp1.core.domain.model.UserModel
import org.ucb.appp1.core.domain.vo.Email
import org.ucb.appp1.core.domain.vo.Password
import org.ucb.appp1.feature.login.domain.repository.AuthRepository

/**
 * Usuario de prueba ya cargado: test@ucb.edu.bo / 123456
 * (además de cualquier cuenta que se cree desde SignUp, ya que
 * comparten el mismo InMemoryAuthStore).
 */
class AuthRepositoryImpl : AuthRepository {
    override suspend fun login(email: Email, password: Password): Result<UserModel> =
        InMemoryAuthStore.login(email, password.value)
}
