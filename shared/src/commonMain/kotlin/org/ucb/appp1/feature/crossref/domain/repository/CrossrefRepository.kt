package org.ucb.appp1.feature.crossref.domain.repository

import org.ucb.appp1.feature.crossref.domain.model.CrossrefModel

interface CrossrefRepository {
    suspend fun getItems(): Result<List<CrossrefModel>>
}