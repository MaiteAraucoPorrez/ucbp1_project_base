package org.ucb.appp1.feature.pushnotifications.data.datasource

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.Direction
import dev.gitlive.firebase.firestore.firestore
import dev.gitlive.firebase.messaging.messaging
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.ucb.appp1.feature.pushnotifications.data.dto.DeviceTokenDto
import org.ucb.appp1.feature.pushnotifications.domain.model.PushSegment

class PushTokenDataSource {

    private val tokens get() = Firebase.firestore.collection(COLLECTION)

    // ---- Firebase Cloud Messaging ----
    suspend fun fetchToken(): String = Firebase.messaging.getToken()

    suspend fun deleteToken() = Firebase.messaging.deleteToken()

    fun subscribe(segment: PushSegment) = Firebase.messaging.subscribeToTopic(segment.topic)

    fun unsubscribe(segment: PushSegment) = Firebase.messaging.unsubscribeFromTopic(segment.topic)

    // ---- Lista de tokens en Firestore ----
    suspend fun save(token: String, dto: DeviceTokenDto) {
        // El id del documento es el token: registrar dos veces el mismo token no crea duplicados.
        tokens.document(token).set(dto)
    }

    suspend fun remove(token: String) {
        tokens.document(token).delete()
    }

    fun observe(): Flow<List<DeviceTokenDto>> =
        tokens.orderBy("updatedAt", Direction.DESCENDING).snapshots.map { snapshot ->
            snapshot.documents.map { it.data<DeviceTokenDto>() }
        }

    private companion object {
        const val COLLECTION = "device_tokens"
    }
}
