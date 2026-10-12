package org.ucb.appp1.feature.announcements.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.ucb.appp1.feature.announcements.domain.model.Announcement
import org.ucb.appp1.feature.announcements.domain.repository.AnnouncementRepository

class ObserveAnnouncementsUseCase(
    private val repository: AnnouncementRepository
) {
    operator fun invoke(): Flow<List<Announcement>> = repository.observeAnnouncements()
}

class AddAnnouncementUseCase(
    private val repository: AnnouncementRepository
) {
    suspend operator fun invoke(title: String, message: String): Result<Unit> =
        repository.add(title.trim(), message.trim())
}

class DeleteAnnouncementUseCase(
    private val repository: AnnouncementRepository
) {
    suspend operator fun invoke(id: String): Result<Unit> = repository.delete(id)
}
