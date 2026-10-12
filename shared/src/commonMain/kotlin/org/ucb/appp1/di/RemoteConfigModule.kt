package org.ucb.appp1.di

import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.ucb.appp1.feature.remoteconfig.data.datasource.RemoteConfigDataSource
import org.ucb.appp1.feature.remoteconfig.data.repository.AppConfigRepositoryImpl
import org.ucb.appp1.feature.remoteconfig.domain.repository.AppConfigRepository
import org.ucb.appp1.feature.remoteconfig.domain.usecase.GetAppConfigUseCase
import org.ucb.appp1.feature.remoteconfig.presentation.viewmodel.RemoteConfigViewModel

val remoteConfigModule = module {
    singleOf(::RemoteConfigDataSource)
    single<AppConfigRepository> { AppConfigRepositoryImpl(get()) }
    singleOf(::GetAppConfigUseCase)
    viewModelOf(::RemoteConfigViewModel)
}
