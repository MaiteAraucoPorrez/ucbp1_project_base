package org.ucb.appp1.feature.pushnotifications.data.repository

import kotlin.coroutines.cancellation.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.ucb.appp1.core.util.currentTimeMillis
import org.ucb.appp1.feature.pushnotifications.data.datasource.PushTokenDataSource
import org.ucb.appp1.feature.pushnotifications.data.dto.DeviceTokenDto
import org.ucb.appp1.feature.pushnotifications.data.mapper.toDomain
import org.ucb.appp1.feature.pushnotifications.domain.model.DeviceToken
import org.ucb.appp1.feature.pushnotifications.domain.model.PushSegment
import org.ucb.appp1.feature.pushnotifications.domain.repository.PushTokenRepository
import org.ucb.appp1.getPlatform

class PushTokenRepositoryImpl(
    private val dataSource: PushTokenDataSource
) : PushTokenRepository {

    override suspend fun getCurrentToken(): Result<String> =
        safeCall { dataSource.fetchToken() }

    override suspend fun registerToken(segment: PushSegment): Result<DeviceToken> = safeCall {
        val token = dataSource.fetchToken()

        // Un dispositivo pertenece a un solo segmento a la vez.
        PushSegment.entries.filter { it != segment }.forEach { dataSource.unsubscribe(it) }
        dataSource.subscribe(segment)

        val dto = DeviceTokenDto(
            token = token,
            appId = APP_ID,
            platform = getPlatform().name,
            segment = segment.name.lowercase(),
            updatedAt = currentTimeMillis()
        )
        dataSource.save(token, dto)
        dto.toDomain()
    }

    override suspend fun deleteCurrentToken(): Result<Unit> = safeCall {
        val token = dataSource.fetchToken()
        PushSegment.entries.forEach { dataSource.unsubscribe(it) }
        dataSource.remove(token)
        dataSource.deleteToken()
    }

    override fun observeTokens(): Flow<List<DeviceToken>> =
        dataSource.observe().map { list -> list.map { it.toDomain() } }

    private suspend fun <T> safeCall(block: suspend () -> T): Result<T> {
        return try {
            Result.success(block())
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private companion object {
        /** Identificador de la app (pizarra: "appid"). Es el mismo applicationId de Android. */
        const val APP_ID = "org.ucb.appp1"
    }
}
