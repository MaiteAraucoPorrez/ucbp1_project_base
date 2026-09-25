package org.ucb.appp1.feature.login.domain.repository

import org.ucb.appp1.core.domain.model.UserModel
import org.ucb.appp1.core.domain.vo.Email
import org.ucb.appp1.core.domain.vo.Password

interface AuthRepository {
    suspend fun login(email: Email, password: Password): Result<UserModel>
}
