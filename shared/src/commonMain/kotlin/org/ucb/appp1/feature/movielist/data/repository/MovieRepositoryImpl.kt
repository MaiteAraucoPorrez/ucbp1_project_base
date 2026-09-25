package org.ucb.appp1.feature.movielist.data.repository

import org.ucb.appp1.feature.movielist.domain.model.Movie
import org.ucb.appp1.feature.movielist.domain.repository.MovieRepository

/**
 * Implementación TEMPORAL sin backend real. Es un `class` normal (no
 * `object`) registrado como `single` en Koin (DataModule), así que
 * sigue habiendo una sola instancia/lista compartida para toda la app
 * — MovieListScreen y MovieDetailScreen verán los mismos datos.
 */
class MovieRepositoryImpl : MovieRepository {

    private val movies = mutableListOf(
        Movie("1", "The Matrix", "", listOf("Ciencia Ficción", "Acción"), 4.8, false),
        Movie("2", "Spider-Man", "", listOf("Acción", "Aventura"), 4.5, false),
        Movie("3", "Interstellar", "", listOf("Ciencia Ficción", "Drama"), 4.9, true)
    )

    override suspend fun getMovies(query: String): List<Movie> =
        movies.filter { it.title.contains(query, ignoreCase = true) }

    override suspend fun getMovieById(movieId: String): Movie? =
        movies.find { it.id == movieId }

    override suspend fun toggleFavorite(movieId: String) {
        val index = movies.indexOfFirst { it.id == movieId }
        if (index != -1) {
            movies[index] = movies[index].copy(isFavorite = !movies[index].isFavorite)
        }
    }
}
