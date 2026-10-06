package org.ucb.appp1.exchangerate.domain.repository

import org.ucb.appp1.exchangerate.domain.model.ExchangeRateModel

interface ExchangeRateRepository {
    suspend fun getList(): List<ExchangeRateModel>

    suspend fun insert(exchangeRate: ExchangeRateModel)
}
