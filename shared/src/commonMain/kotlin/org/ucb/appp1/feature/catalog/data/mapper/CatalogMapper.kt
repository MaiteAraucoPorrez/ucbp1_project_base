package org.ucb.appp1.feature.catalog.data.mapper

import org.ucb.appp1.feature.catalog.data.dto.MovieDto
import org.ucb.appp1.feature.catalog.domain.model.MovieModel

private const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w500"

fun MovieDto.toModel(): MovieModel = MovieModel(
    id = id,
    title = title,
    posterUrl = posterPath?.let { POSTER_BASE_URL + it }
)