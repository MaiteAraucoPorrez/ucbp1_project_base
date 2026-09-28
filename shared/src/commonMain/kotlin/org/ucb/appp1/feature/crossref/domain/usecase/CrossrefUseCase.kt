package org.ucb.appp1.feature.crossref.domain.usecase

import org.ucb.appp1.feature.crossref.domain.model.CrossrefModel
import org.ucb.appp1.feature.crossref.domain.repository.CrossrefRepository

class GetCrossrefUseCase(
    private val repository: CrossrefRepository
) {
    suspend operator fun invoke(): Result<List<CrossrefModel>> = repository.getItems()
}