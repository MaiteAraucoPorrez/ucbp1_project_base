package org.ucb.appp1.feature.announcements.data.repository

import org.ucb.appp1.core.util.safeCall
import kotlinx.coroutines.flow.Flow
import org.ucb.appp1.feature.announcements.data.datasource.AnnouncementFirestoreDataSource
import org.ucb.appp1.feature.announcements.domain.model.Announcement
import org.ucb.appp1.feature.announcements.domain.repository.AnnouncementRepository

class AnnouncementRepositoryImpl(
    private val dataSource: AnnouncementFirestoreDataSource
) : AnnouncementRepository {

    override fun observeAnnouncements(): Flow<List<Announcement>> = dataSource.observe()

    override suspend fun add(title: String, message: String): Result<Unit> =
        safeCall { dataSource.add(title, message) }

    override suspend fun delete(id: String): Result<Unit> =
        safeCall { dataSource.delete(id) }

}
