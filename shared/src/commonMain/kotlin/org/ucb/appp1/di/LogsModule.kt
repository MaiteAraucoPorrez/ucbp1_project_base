package org.ucb.appp1.di

import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.ucb.appp1.feature.logs.data.config.LogsServerConfig
import org.ucb.appp1.feature.logs.data.datasource.LogsRemoteDataSource
import org.ucb.appp1.feature.logs.data.repository.LogsRepositoryImpl
import org.ucb.appp1.feature.logs.data.service.LogsGrpcService
import org.ucb.appp1.feature.logs.domain.repository.LogsRepository
import org.ucb.appp1.feature.logs.domain.usecase.SendLogsUseCase
import org.ucb.appp1.feature.logs.presentation.viewmodel.LogsViewModel

val logsModule = module {
    singleOf(::LogsServerConfig)
    single<LogsRemoteDataSource> { LogsGrpcService(get()) }
    single<LogsRepository> { LogsRepositoryImpl(get()) }
    singleOf(::SendLogsUseCase)
    viewModelOf(::LogsViewModel)
}
