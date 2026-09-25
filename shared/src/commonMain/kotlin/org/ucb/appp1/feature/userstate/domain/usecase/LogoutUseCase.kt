package org.ucb.appp1.feature.userstate.domain.usecase

import org.ucb.appp1.core.domain.repository.SessionRepository

class LogoutUseCase(private val session: SessionRepository) {
    operator fun invoke() = session.clearSession()
}
