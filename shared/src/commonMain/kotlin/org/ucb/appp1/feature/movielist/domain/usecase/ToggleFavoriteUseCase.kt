package org.ucb.appp1.feature.movielist.domain.usecase

import org.ucb.appp1.feature.movielist.domain.repository.MovieRepository

class ToggleFavoriteUseCase(private val repository: MovieRepository) {
    suspend operator fun invoke(movieId: String) = repository.toggleFavorite(movieId)
}
