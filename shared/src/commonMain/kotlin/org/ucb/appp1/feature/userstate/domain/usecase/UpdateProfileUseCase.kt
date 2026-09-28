package org.ucb.appp1.feature.userstate.domain.usecase

import org.ucb.appp1.core.domain.model.UserModel
import org.ucb.appp1.core.domain.repository.ProfileRepository
import org.ucb.appp1.core.domain.repository.SessionRepository

class UpdateProfileUseCase(
    private val profileRepository: ProfileRepository,
    private val session: SessionRepository
) {
    suspend operator fun invoke(fullName: String): Result<UserModel> {
        val currentUser = session.getCurrentUser()
            ?: return Result.failure(IllegalStateException("No hay una sesión activa"))
        if (fullName.isBlank()) {
            return Result.failure(IllegalArgumentException("El nombre no puede estar vacío"))
        }
        return profileRepository.updateFullName(currentUser.email, fullName)
            .onSuccess { session.setCurrentUser(it) }
    }
}
