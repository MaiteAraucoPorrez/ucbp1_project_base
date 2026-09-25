package org.ucb.appp1.di

import org.koin.dsl.module
import org.ucb.appp1.feature.login.data.repository.AuthRepositoryImpl
import org.ucb.appp1.feature.login.domain.repository.AuthRepository
import org.ucb.appp1.feature.movielist.data.repository.MovieRepositoryImpl
import org.ucb.appp1.feature.movielist.domain.repository.MovieRepository
import org.ucb.appp1.feature.signup.data.repository.RegisterRepositoryImpl
import org.ucb.appp1.feature.signup.domain.repository.RegisterRepository

val dataModule = module {
    single<AuthRepository> { AuthRepositoryImpl() }
    single<RegisterRepository> { RegisterRepositoryImpl() }
    single<MovieRepository> { MovieRepositoryImpl() }
    // TODO: cuando agreguemos MovieDetail, Perfil y UserInformation,
    // sus repositorios se registran aquí con single<Interfaz> { Impl() }
}
