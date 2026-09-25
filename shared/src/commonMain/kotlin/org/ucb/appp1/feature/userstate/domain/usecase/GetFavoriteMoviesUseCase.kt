package org.ucb.appp1.feature.userstate.domain.usecase

import org.ucb.appp1.feature.movielist.domain.model.Movie
import org.ucb.appp1.feature.movielist.domain.repository.MovieRepository

class GetFavoriteMoviesUseCase(private val repository: MovieRepository) {
    suspend operator fun invoke(): List<Movie> = repository.getFavoriteMovies()
}
