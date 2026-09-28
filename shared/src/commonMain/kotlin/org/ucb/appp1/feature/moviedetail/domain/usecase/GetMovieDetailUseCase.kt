package org.ucb.appp1.feature.moviedetail.domain.usecase

import org.ucb.appp1.feature.movielist.domain.model.Movie
import org.ucb.appp1.feature.movielist.domain.repository.MovieRepository
import org.ucb.appp1.feature.movielist.domain.vo.MovieId

class GetMovieDetailUseCase(private val repository: MovieRepository) {
    suspend operator fun invoke(movieId: MovieId): Movie? = repository.getMovieById(movieId)
}
