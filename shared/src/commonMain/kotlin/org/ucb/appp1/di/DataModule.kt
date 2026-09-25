package org.ucb.appp1.di

import org.koin.dsl.module
import org.ucb.appp1.core.data.SessionRepositoryImpl
import org.ucb.appp1.core.domain.repository.SessionRepository
import org.ucb.appp1.feature.login.data.repository.AuthRepositoryImpl
import org.ucb.appp1.feature.login.domain.repository.AuthRepository
import org.ucb.appp1.feature.movielist.data.repository.MovieRepositoryImpl
import org.ucb.appp1.feature.movielist.domain.repository.MovieRepository
import org.ucb.appp1.feature.signup.data.repository.RegisterRepositoryImpl
import org.ucb.appp1.feature.signup.domain.repository.RegisterRepository
import org.ucb.appp1.userinformation.data.datasource.GithubRemoteDataSource
import org.ucb.appp1.userinformation.data.repository.GithubRepositoryImpl
import org.ucb.appp1.userinformation.data.service.GitHubApiService
import org.ucb.appp1.userinformation.domain.repository.GithubRepository

val dataModule = module {
    single<AuthRepository> { AuthRepositoryImpl() }
    single<RegisterRepository> { RegisterRepositoryImpl() }
    single<MovieRepository> { MovieRepositoryImpl() }
    single<SessionRepository> { SessionRepositoryImpl() }
    single<GithubRemoteDataSource> { GitHubApiService() }
    single<GithubRepository> { GithubRepositoryImpl(get()) }
}
