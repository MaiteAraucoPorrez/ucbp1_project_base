package org.ucb.appp1.feature.signup.domain.repository

import org.ucb.appp1.core.domain.model.UserModel
import org.ucb.appp1.core.domain.vo.Email

interface RegisterRepository {
    suspend fun register(fullName: String, email: Email, password: String): Result<UserModel>
}
