package org.ucb.appp1.feature.signup.data.repository

import org.ucb.appp1.core.data.InMemoryAuthStore
import org.ucb.appp1.core.domain.model.UserModel
import org.ucb.appp1.core.domain.vo.Email
import org.ucb.appp1.feature.signup.domain.repository.RegisterRepository

class RegisterRepositoryImpl : RegisterRepository {
    override suspend fun register(fullName: String, email: Email, password: String): Result<UserModel> =
        InMemoryAuthStore.register(fullName, email, password)
}
