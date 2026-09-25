package org.ucb.appp1.feature.userstate.domain.usecase

import org.ucb.appp1.core.domain.model.UserModel
import org.ucb.appp1.core.domain.repository.SessionRepository

class GetCurrentUserUseCase(private val session: SessionRepository) {
    operator fun invoke(): UserModel? = session.getCurrentUser()
}
