package org.ucb.appp1.feature.announcements.domain.repository

import kotlinx.coroutines.flow.Flow
import org.ucb.appp1.feature.announcements.domain.model.Announcement

interface AnnouncementRepository {
    /** Emite la lista completa cada vez que cambia en Firestore (tiempo real, sin refresh). */
    fun observeAnnouncements(): Flow<List<Announcement>>

    suspend fun add(title: String, message: String): Result<Unit>

    suspend fun delete(id: String): Result<Unit>
}
