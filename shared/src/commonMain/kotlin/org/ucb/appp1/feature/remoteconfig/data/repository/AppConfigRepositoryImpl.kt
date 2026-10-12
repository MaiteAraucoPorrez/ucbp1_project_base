package org.ucb.appp1.feature.remoteconfig.data.repository

import org.ucb.appp1.feature.remoteconfig.data.datasource.RemoteConfigDataSource
import org.ucb.appp1.feature.remoteconfig.data.mapper.toDomain
import org.ucb.appp1.feature.remoteconfig.domain.model.AppConfig
import org.ucb.appp1.feature.remoteconfig.domain.repository.AppConfigRepository

class AppConfigRepositoryImpl(
    private val dataSource: RemoteConfigDataSource
) : AppConfigRepository {

    override suspend fun getConfig(): Result<AppConfig> {
        return dataSource.fetch().map { it.toDomain() }
    }
}
