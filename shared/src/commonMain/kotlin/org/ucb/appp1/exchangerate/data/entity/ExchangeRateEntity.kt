package org.ucb.appp1.exchangerate.data.entity

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "exchange_rates")
data class ExchangeRateEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "exchange_official")
    var exchangeOfficial: String? = null,

    @ColumnInfo(name = "exchange_parallel")
    var exchangeParallel: String? = null,

    @ColumnInfo(name = "timestamp")
    var timestamp: Long = 0
)
