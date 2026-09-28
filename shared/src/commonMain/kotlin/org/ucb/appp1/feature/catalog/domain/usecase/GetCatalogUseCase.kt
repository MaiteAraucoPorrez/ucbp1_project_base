package org.ucb.appp1.feature.catalog.domain.usecase

import org.ucb.appp1.feature.catalog.domain.model.MovieModel
import org.ucb.appp1.feature.catalog.domain.repository.CatalogRepository

class GetCatalogUseCase(
    private val repository: CatalogRepository
) {
    suspend operator fun invoke(): Result<List<MovieModel>> = repository.getMovies()
}