package org.ucb.appp1.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.ucb.appp1.feature.login.domain.usecase.LoginUseCase
import org.ucb.appp1.feature.moviedetail.domain.usecase.GetMovieDetailUseCase
import org.ucb.appp1.feature.movielist.domain.usecase.GetMoviesUseCase
import org.ucb.appp1.feature.movielist.domain.usecase.ToggleFavoriteUseCase
import org.ucb.appp1.feature.signup.domain.usecase.RegisterUseCase
import org.ucb.appp1.feature.userstate.domain.usecase.GetCurrentUserUseCase
import org.ucb.appp1.feature.userstate.domain.usecase.GetFavoriteMoviesUseCase
import org.ucb.appp1.feature.userstate.domain.usecase.LogoutUseCase

val domainModule = module {
    singleOf(::LoginUseCase)
    singleOf(::RegisterUseCase)
    singleOf(::GetMoviesUseCase)
    singleOf(::ToggleFavoriteUseCase)
    singleOf(::GetMovieDetailUseCase)
    singleOf(::GetCurrentUserUseCase)
    singleOf(::GetFavoriteMoviesUseCase)
    singleOf(::LogoutUseCase)
}
