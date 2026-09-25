package org.ucb.appp1.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.ucb.appp1.feature.login.presentation.LoginViewModel
import org.ucb.appp1.feature.movielist.presentation.MovieListViewModel
import org.ucb.appp1.feature.signup.presentation.RegisterViewModel

val presentationModule = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)
    viewModelOf(::MovieListViewModel)
    // TODO: cuando agreguemos MovieDetail, Perfil y UserInformation,
    // sus ViewModels se registran aquí con viewModelOf(::MiViewModel)
}
