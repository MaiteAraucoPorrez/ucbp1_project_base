package org.ucb.appp1.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import org.ucb.appp1.feature.catalog.data.datasource.CatalogRemoteDataSource
import org.ucb.appp1.feature.catalog.data.repository.CatalogRepositoryImpl
import org.ucb.appp1.feature.catalog.data.service.CatalogService
import org.ucb.appp1.feature.catalog.domain.repository.CatalogRepository
import org.ucb.appp1.feature.crossref.data.datasource.CrossrefRemoteDataSource
import org.ucb.appp1.feature.crossref.data.repository.CrossrefRepositoryImpl
import org.ucb.appp1.feature.crossref.data.service.CrossrefService
import org.ucb.appp1.feature.crossref.domain.repository.CrossrefRepository
import org.ucb.appp1.core.data.ProfileRepositoryImpl
import org.ucb.appp1.core.data.SessionRepositoryImpl
import org.ucb.appp1.core.domain.repository.ProfileRepository
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
import org.koin.core.module.dsl.singleOf
import org.ucb.appp1.config.AppDatabase
import org.ucb.appp1.exchangerate.data.dao.ExchangeRateDao
import org.ucb.appp1.exchangerate.data.datasource.ExchangeRateLocalDataSource
import org.ucb.appp1.exchangerate.data.repository.ExchangeRateRepositoryImpl
import org.ucb.appp1.exchangerate.domain.repository.ExchangeRateRepository
import org.ucb.appp1.exchange.data.datasource.RealTimeDataBase
import org.ucb.appp1.exchange.data.repository.ExchangeRepositoryImpl
import org.ucb.appp1.exchange.domain.repository.ExchangeRepository

val dataModule = module {
    single<AuthRepository> { AuthRepositoryImpl() }
    single<RegisterRepository> { RegisterRepositoryImpl() }
    single<MovieRepository> { MovieRepositoryImpl() }
    single<SessionRepository> { SessionRepositoryImpl() }
    single<ProfileRepository> { ProfileRepositoryImpl() }
    single<GithubRemoteDataSource> { GitHubApiService() }
    single<GithubRepository> { GithubRepositoryImpl(get()) }

    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true; isLenient = true })
            }
        }
    }

    // --- Catalog (TMDB) ---
    single<CatalogRemoteDataSource> { CatalogService(get()) }
    single<CatalogRepository> { CatalogRepositoryImpl(get()) }

    // --- Crossref ---
    single<CrossrefRemoteDataSource> { CrossrefService(get()) }
    single<CrossrefRepository> { CrossrefRepositoryImpl(get()) }

    single<ExchangeRateDao> { get<AppDatabase>().getDao() }
    singleOf(::ExchangeRateLocalDataSource)
    single<ExchangeRateRepository> { ExchangeRateRepositoryImpl(get()) }

    singleOf(::RealTimeDataBase)
    single<ExchangeRepository> { ExchangeRepositoryImpl(get()) }
}
