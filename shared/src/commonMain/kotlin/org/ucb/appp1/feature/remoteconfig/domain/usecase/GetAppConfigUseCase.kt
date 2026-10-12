package org.ucb.appp1.feature.remoteconfig.domain.usecase

import org.ucb.appp1.feature.remoteconfig.domain.model.AppConfig
import org.ucb.appp1.feature.remoteconfig.domain.repository.AppConfigRepository

class GetAppConfigUseCase(
    private val repository: AppConfigRepository
) {
    suspend operator fun invoke(): Result<AppConfig> = repository.getConfig()
}
