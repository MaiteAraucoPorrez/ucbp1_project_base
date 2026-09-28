package org.ucb.appp1.feature.crossref.data.repository

import org.ucb.appp1.feature.crossref.data.datasource.CrossrefRemoteDataSource
import org.ucb.appp1.feature.crossref.domain.model.CrossrefModel
import org.ucb.appp1.feature.crossref.domain.repository.CrossrefRepository

class CrossrefRepositoryImpl(
    private val dataSource: CrossrefRemoteDataSource
) : CrossrefRepository {

    override suspend fun getItems(): Result<List<CrossrefModel>> {
        return dataSource.fetchData()
    }
}