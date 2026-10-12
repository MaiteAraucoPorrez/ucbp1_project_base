package org.ucb.appp1.feature.announcements.data.datasource

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.Direction
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.ucb.appp1.core.util.currentTimeMillis
import org.ucb.appp1.feature.announcements.data.dto.AnnouncementDto
import org.ucb.appp1.feature.announcements.data.mapper.toDomain
import org.ucb.appp1.feature.announcements.domain.model.Announcement

class AnnouncementFirestoreDataSource {

    private val collection get() = Firebase.firestore.collection(COLLECTION)

    fun observe(): Flow<List<Announcement>> =
        collection
            .orderBy("createdAt", Direction.DESCENDING)
            .snapshots
            .map { snapshot ->
                snapshot.documents.map { doc -> doc.data<AnnouncementDto>().toDomain(doc.id) }
            }

    suspend fun add(title: String, message: String) {
        collection.add(AnnouncementDto(title = title, message = message, createdAt = currentTimeMillis()))
    }

    suspend fun delete(id: String) {
        collection.document(id).delete()
    }

    private companion object {
        const val COLLECTION = "announcements"
    }
}
