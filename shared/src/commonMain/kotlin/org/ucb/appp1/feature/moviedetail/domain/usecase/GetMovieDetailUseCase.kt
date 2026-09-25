package org.ucb.appp1.feature.moviedetail.domain.usecase

import org.ucb.appp1.feature.movielist.domain.model.Movie
import org.ucb.appp1.feature.movielist.domain.repository.MovieRepository

class GetMovieDetailUseCase(private val repository: MovieRepository) {
    suspend operator fun invoke(movieId: String): Movie? = repository.getMovieById(movieId)
}
