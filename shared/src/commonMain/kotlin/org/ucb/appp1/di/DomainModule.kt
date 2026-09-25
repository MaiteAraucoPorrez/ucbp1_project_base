package org.ucb.appp1.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.ucb.appp1.feature.login.domain.usecase.LoginUseCase
import org.ucb.appp1.feature.movielist.domain.usecase.GetMoviesUseCase
import org.ucb.appp1.feature.movielist.domain.usecase.ToggleFavoriteUseCase
import org.ucb.appp1.feature.signup.domain.usecase.RegisterUseCase

val domainModule = module {
    singleOf(::LoginUseCase)
    singleOf(::RegisterUseCase)
    singleOf(::GetMoviesUseCase)
    singleOf(::ToggleFavoriteUseCase)
    // TODO: cuando agreguemos MovieDetail, Perfil y UserInformation,
    // sus Use Cases se registran aquí con singleOf(::MiUseCase)
}
