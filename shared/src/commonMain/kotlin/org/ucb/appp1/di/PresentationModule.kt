package org.ucb.appp1.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.ucb.appp1.feature.login.presentation.LoginViewModel
import org.ucb.appp1.feature.moviedetail.presentation.MovieDetailViewModel
import org.ucb.appp1.feature.movielist.presentation.MovieListViewModel
import org.ucb.appp1.feature.signup.presentation.RegisterViewModel
import org.ucb.appp1.feature.userstate.presentation.UserStateViewModel
import org.ucb.appp1.userinformation.presentation.UserInformationViewModel

val presentationModule = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)
    viewModelOf(::MovieListViewModel)
    viewModelOf(::MovieDetailViewModel)
    viewModelOf(::UserStateViewModel)
    viewModelOf(::UserInformationViewModel)
}
