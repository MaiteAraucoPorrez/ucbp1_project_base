package org.ucb.appp1.exchangerate.data.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import org.ucb.appp1.exchangerate.data.entity.ExchangeRateEntity

@Dao
interface ExchangeRateDao {
    @Query("SELECT * FROM exchange_rates")
    suspend fun getList(): List<ExchangeRateEntity>

    @Query("SELECT * FROM exchange_rates WHERE id = :id")
    suspend fun getById(id: Int): ExchangeRateEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(exchangeRate: ExchangeRateEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertExchangeRates(lists: List<ExchangeRateEntity>)

    @Query("DELETE FROM exchange_rates")
    suspend fun deleteAll()
}
