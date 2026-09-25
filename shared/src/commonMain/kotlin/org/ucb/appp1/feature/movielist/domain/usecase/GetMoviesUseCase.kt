package org.ucb.appp1.feature.movielist.domain.usecase

import org.ucb.appp1.feature.movielist.domain.model.Movie
import org.ucb.appp1.feature.movielist.domain.repository.MovieRepository

class GetMoviesUseCase(private val repository: MovieRepository) {
    suspend operator fun invoke(query: String): List<Movie> = repository.getMovies(query)
}
