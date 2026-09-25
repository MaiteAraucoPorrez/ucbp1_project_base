package org.ucb.appp1.feature.movielist.data.repository

import org.ucb.appp1.feature.movielist.domain.model.Movie
import org.ucb.appp1.feature.movielist.domain.repository.MovieRepository

class MovieRepositoryImpl : MovieRepository {

    private val movies = mutableListOf(
        Movie(
            id = "1", title = "The Matrix", posterUrl = "",
            genres = listOf("Ciencia Ficción", "Acción"), rating = 4.8, isFavorite = false,
            synopsis = "Un programador descubre que la realidad es una simulación controlada por máquinas."
        ),
        Movie(
            id = "2", title = "Spider-Man", posterUrl = "",
            genres = listOf("Acción", "Aventura"), rating = 4.5, isFavorite = false,
            synopsis = "Un joven adquiere poderes arácnidos y debe aprender a usarlos con responsabilidad."
        ),
        Movie(
            id = "3", title = "Interstellar", posterUrl = "",
            genres = listOf("Ciencia Ficción", "Drama"), rating = 4.9, isFavorite = true,
            synopsis = "Un grupo de astronautas viaja por un agujero de gusano buscando un nuevo hogar para la humanidad."
        )
    )

    override suspend fun getMovies(query: String): List<Movie> =
        movies.filter { it.title.contains(query, ignoreCase = true) }

    override suspend fun getMovieById(movieId: String): Movie? =
        movies.find { it.id == movieId }

    override suspend fun getFavoriteMovies(): List<Movie> =
        movies.filter { it.isFavorite }

    override suspend fun toggleFavorite(movieId: String) {
        val index = movies.indexOfFirst { it.id == movieId }
        if (index != -1) {
            movies[index] = movies[index].copy(isFavorite = !movies[index].isFavorite)
        }
    }
}
