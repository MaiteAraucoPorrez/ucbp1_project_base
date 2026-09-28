package org.ucb.appp1.feature.movielist.domain.usecase

import org.ucb.appp1.feature.movielist.domain.repository.MovieRepository
import org.ucb.appp1.feature.movielist.domain.vo.MovieId

class ToggleFavoriteUseCase(private val repository: MovieRepository) {
    suspend operator fun invoke(movieId: MovieId) = repository.toggleFavorite(movieId)
}
