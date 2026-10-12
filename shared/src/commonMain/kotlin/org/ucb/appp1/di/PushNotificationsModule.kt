package org.ucb.appp1.di

import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.ucb.appp1.feature.pushnotifications.data.datasource.PushTokenDataSource
import org.ucb.appp1.feature.pushnotifications.data.repository.PushTokenRepositoryImpl
import org.ucb.appp1.feature.pushnotifications.domain.repository.PushTokenRepository
import org.ucb.appp1.feature.pushnotifications.domain.usecase.DeleteDeviceTokenUseCase
import org.ucb.appp1.feature.pushnotifications.domain.usecase.GetCurrentTokenUseCase
import org.ucb.appp1.feature.pushnotifications.domain.usecase.ObserveDeviceTokensUseCase
import org.ucb.appp1.feature.pushnotifications.domain.usecase.RegisterDeviceTokenUseCase
import org.ucb.appp1.feature.pushnotifications.presentation.viewmodel.PushViewModel

val pushNotificationsModule = module {
    singleOf(::PushTokenDataSource)
    single<PushTokenRepository> { PushTokenRepositoryImpl(get()) }
    singleOf(::GetCurrentTokenUseCase)
    singleOf(::RegisterDeviceTokenUseCase)
    singleOf(::DeleteDeviceTokenUseCase)
    singleOf(::ObserveDeviceTokensUseCase)
    viewModelOf(::PushViewModel)
}
