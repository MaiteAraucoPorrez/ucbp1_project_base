package org.ucb.appp1.di

import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.ucb.appp1.feature.announcements.data.datasource.AnnouncementFirestoreDataSource
import org.ucb.appp1.feature.announcements.data.repository.AnnouncementRepositoryImpl
import org.ucb.appp1.feature.announcements.domain.repository.AnnouncementRepository
import org.ucb.appp1.feature.announcements.domain.usecase.AddAnnouncementUseCase
import org.ucb.appp1.feature.announcements.domain.usecase.DeleteAnnouncementUseCase
import org.ucb.appp1.feature.announcements.domain.usecase.ObserveAnnouncementsUseCase
import org.ucb.appp1.feature.announcements.presentation.viewmodel.AnnouncementsViewModel

val announcementsModule = module {
    singleOf(::AnnouncementFirestoreDataSource)
    single<AnnouncementRepository> { AnnouncementRepositoryImpl(get()) }
    singleOf(::ObserveAnnouncementsUseCase)
    singleOf(::AddAnnouncementUseCase)
    singleOf(::DeleteAnnouncementUseCase)
    viewModelOf(::AnnouncementsViewModel)
}
