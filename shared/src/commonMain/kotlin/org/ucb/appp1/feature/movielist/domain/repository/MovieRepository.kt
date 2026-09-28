package org.ucb.appp1.feature.movielist.domain.repository

import org.ucb.appp1.feature.movielist.domain.model.Movie
import org.ucb.appp1.feature.movielist.domain.vo.MovieId

interface MovieRepository {
    suspend fun getMovies(query: String): List<Movie>
    suspend fun getMovieById(movieId: MovieId): Movie?
    suspend fun getFavoriteMovies(): List<Movie>
    suspend fun toggleFavorite(movieId: MovieId)
}
