package org.ucb.appp1.feature.crossref.data.datasource

import org.ucb.appp1.feature.crossref.domain.model.CrossrefModel

interface CrossrefRemoteDataSource {
    suspend fun fetchData(): Result<List<CrossrefModel>>
}