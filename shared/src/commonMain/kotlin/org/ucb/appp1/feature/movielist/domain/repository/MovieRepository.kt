package org.ucb.appp1.feature.movielist.domain.repository

import org.ucb.appp1.feature.movielist.domain.model.Movie

interface MovieRepository {
    suspend fun getMovies(query: String): List<Movie>
    suspend fun getMovieById(movieId: String): Movie?
    suspend fun toggleFavorite(movieId: String)
}
