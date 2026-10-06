package org.ucb.appp1.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.ucb.appp1.feature.login.presentation.viewmodel.LoginViewModel
import org.ucb.appp1.feature.moviedetail.presentation.viewmodel.MovieDetailViewModel
import org.ucb.appp1.feature.movielist.presentation.viewmodel.MovieListViewModel
import org.ucb.appp1.feature.signup.presentation.viewmodel.RegisterViewModel
import org.ucb.appp1.feature.userstate.presentation.viewmodel.UserStateEditViewModel
import org.ucb.appp1.feature.userstate.presentation.viewmodel.UserStateViewModel
import org.ucb.appp1.userinformation.presentation.viewmodel.UserInformationViewModel
import org.ucb.appp1.feature.catalog.presentation.viewmodel.CatalogViewModel
import org.ucb.appp1.feature.crossref.presentation.viewmodel.CrossrefViewModel
import org.ucb.appp1.exchangerate.presentation.viewmodel.ExchangeRateViewModel
import org.ucb.appp1.exchange.presentation.viewmodel.ExchangeViewModel

val presentationModule = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)
    viewModelOf(::MovieListViewModel)
    viewModelOf(::MovieDetailViewModel)
    viewModelOf(::UserStateViewModel)
    viewModelOf(::UserStateEditViewModel)
    viewModelOf(::UserInformationViewModel)
    viewModelOf(::CatalogViewModel)
    viewModelOf(::CrossrefViewModel)
    viewModelOf(::ExchangeRateViewModel)
    viewModelOf(::ExchangeViewModel)
}
