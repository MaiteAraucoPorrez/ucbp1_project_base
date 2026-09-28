package org.ucb.appp1.feature.catalog.data.datasource

import org.ucb.appp1.feature.catalog.domain.model.MovieModel

interface CatalogRemoteDataSource {
    suspend fun fetchData(): Result<List<MovieModel>>
}