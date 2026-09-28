package org.ucb.appp1.feature.movielist.data.repository

import org.ucb.appp1.feature.movielist.domain.model.Movie
import org.ucb.appp1.feature.movielist.domain.repository.MovieRepository
import org.ucb.appp1.feature.movielist.domain.vo.MovieId
import org.ucb.appp1.feature.movielist.domain.vo.PosterPath

class MovieRepositoryImpl : MovieRepository {

    private val movies = mutableListOf(
        Movie(
            id = MovieId("1"), title = "The Matrix", posterPath = PosterPath(""),
            genres = listOf("Ciencia Ficción", "Acción"), rating = 4.8, isFavorite = false,
            synopsis = "Un programador descubre que la realidad es una simulación controlada por máquinas."
        ),
        Movie(
            id = MovieId("2"), title = "Spider-Man", posterPath = PosterPath(""),
            genres = listOf("Acción", "Aventura"), rating = 4.5, isFavorite = false,
            synopsis = "Un joven adquiere poderes arácnidos y debe aprender a usarlos con responsabilidad."
        ),
        Movie(
            id = MovieId("3"), title = "Interstellar", posterPath = PosterPath(""),
            genres = listOf("Ciencia Ficción", "Drama"), rating = 4.9, isFavorite = true,
            synopsis = "Un grupo de astronautas viaja por un agujero de gusano buscando un nuevo hogar para la humanidad."
        )
    )

    override suspend fun getMovies(query: String): List<Movie> =
        movies.filter { it.title.contains(query, ignoreCase = true) }

    override suspend fun getMovieById(movieId: MovieId): Movie? =
        movies.find { it.id == movieId }

    override suspend fun getFavoriteMovies(): List<Movie> =
        movies.filter { it.isFavorite }

    override suspend fun toggleFavorite(movieId: MovieId) {
        val index = movies.indexOfFirst { it.id == movieId }
        if (index != -1) {
            movies[index] = movies[index].copy(isFavorite = !movies[index].isFavorite)
        }
    }
}
