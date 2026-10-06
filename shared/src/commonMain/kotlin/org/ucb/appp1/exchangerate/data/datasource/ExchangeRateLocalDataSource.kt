package org.ucb.appp1.exchangerate.data.datasource

import org.ucb.appp1.exchangerate.data.dao.ExchangeRateDao
import org.ucb.appp1.exchangerate.data.entity.ExchangeRateEntity
import org.ucb.appp1.exchangerate.domain.model.ExchangeRateModel

class ExchangeRateLocalDataSource(
    val dao: ExchangeRateDao
) {
    suspend fun getList(): List<ExchangeRateModel> {
        return dao.getList().map {
            it.toModel()
        }
    }

    suspend fun insert(exchangeRate: ExchangeRateModel) {
        dao.insert(exchangeRate.toEntity())
    }

    private fun ExchangeRateEntity.toModel(): ExchangeRateModel {
        return ExchangeRateModel(
            official = exchangeOfficial ?: "",
            parallel = exchangeParallel ?: ""
        )
    }

    private fun ExchangeRateModel.toEntity() = ExchangeRateEntity(
        exchangeOfficial = official,
        exchangeParallel = parallel
    )
}
