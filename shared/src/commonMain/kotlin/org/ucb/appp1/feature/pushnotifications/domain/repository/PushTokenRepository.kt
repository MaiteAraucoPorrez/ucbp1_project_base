package org.ucb.appp1.feature.pushnotifications.domain.repository

import kotlinx.coroutines.flow.Flow
import org.ucb.appp1.feature.pushnotifications.domain.model.DeviceToken
import org.ucb.appp1.feature.pushnotifications.domain.model.PushSegment

interface PushTokenRepository {
    /** Pide a FCM el token de este dispositivo. */
    suspend fun getCurrentToken(): Result<String>

    /** Guarda el token en Firestore (lista de tokens) y suscribe el dispositivo al segmento. */
    suspend fun registerToken(segment: PushSegment): Result<DeviceToken>

    /** deleteToken: quita el token de la lista, desuscribe los segmentos y borra el token en FCM. */
    suspend fun deleteCurrentToken(): Result<Unit>

    /** Lista de tokens registrados (en tiempo real). */
    fun observeTokens(): Flow<List<DeviceToken>>
}
