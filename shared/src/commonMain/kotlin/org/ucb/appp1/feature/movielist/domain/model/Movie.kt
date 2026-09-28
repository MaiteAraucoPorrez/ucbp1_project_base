package org.ucb.appp1.feature.movielist.domain.model

import org.ucb.appp1.feature.movielist.domain.vo.MovieId
import org.ucb.appp1.feature.movielist.domain.vo.PosterPath

data class Movie(
    val id: MovieId,
    val title: String,
    val posterPath: PosterPath,
    val genres: List<String>,
    val rating: Double,
    val isFavorite: Boolean,
    val synopsis: String = ""
)
