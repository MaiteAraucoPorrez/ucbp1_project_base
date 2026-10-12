package org.ucb.appp1.feature.pushnotifications.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.ucb.appp1.feature.pushnotifications.domain.model.DeviceToken
import org.ucb.appp1.feature.pushnotifications.domain.model.PushSegment
import org.ucb.appp1.feature.pushnotifications.domain.repository.PushTokenRepository

class GetCurrentTokenUseCase(
    private val repository: PushTokenRepository
) {
    suspend operator fun invoke(): Result<String> = repository.getCurrentToken()
}

class RegisterDeviceTokenUseCase(
    private val repository: PushTokenRepository
) {
    suspend operator fun invoke(segment: PushSegment): Result<DeviceToken> =
        repository.registerToken(segment)
}

class DeleteDeviceTokenUseCase(
    private val repository: PushTokenRepository
) {
    suspend operator fun invoke(): Result<Unit> = repository.deleteCurrentToken()
}

class ObserveDeviceTokensUseCase(
    private val repository: PushTokenRepository
) {
    operator fun invoke(): Flow<List<DeviceToken>> = repository.observeTokens()
}
