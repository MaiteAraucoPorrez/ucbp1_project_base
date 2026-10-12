package org.ucb.appp1.feature.announcements.data.repository

import kotlin.coroutines.cancellation.CancellationException
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

    private suspend fun safeCall(block: suspend () -> Unit): Result<Unit> {
        return try {
            block()
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
