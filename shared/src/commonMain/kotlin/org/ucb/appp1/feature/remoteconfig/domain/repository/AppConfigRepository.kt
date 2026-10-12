package org.ucb.appp1.feature.remoteconfig.domain.repository

import org.ucb.appp1.feature.remoteconfig.domain.model.AppConfig

interface AppConfigRepository {
    suspend fun getConfig(): Result<AppConfig>
}
