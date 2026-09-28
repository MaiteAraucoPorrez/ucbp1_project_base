package org.ucb.appp1.feature.catalog.data.repository

import org.ucb.appp1.feature.catalog.domain.model.MovieModel
import org.ucb.appp1.feature.catalog.data.datasource.CatalogRemoteDataSource
import org.ucb.appp1.feature.catalog.domain.repository.CatalogRepository

class CatalogRepositoryImpl(
    private val dataSource: CatalogRemoteDataSource
) : CatalogRepository {

    override suspend fun getMovies(): Result<List<MovieModel>> {
        return dataSource.fetchData()
    }
}