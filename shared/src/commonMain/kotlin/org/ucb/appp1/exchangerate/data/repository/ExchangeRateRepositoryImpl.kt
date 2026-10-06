package org.ucb.appp1.exchangerate.data.repository

import org.ucb.appp1.exchangerate.data.datasource.ExchangeRateLocalDataSource
import org.ucb.appp1.exchangerate.domain.model.ExchangeRateModel
import org.ucb.appp1.exchangerate.domain.repository.ExchangeRateRepository

class ExchangeRateRepositoryImpl(
    val localDataSource: ExchangeRateLocalDataSource
) : ExchangeRateRepository {
    override suspend fun getList(): List<ExchangeRateModel> {
        return localDataSource.getList()
    }

    override suspend fun insert(exchangeRate: ExchangeRateModel) {
        localDataSource.insert(exchangeRate)
    }
}
