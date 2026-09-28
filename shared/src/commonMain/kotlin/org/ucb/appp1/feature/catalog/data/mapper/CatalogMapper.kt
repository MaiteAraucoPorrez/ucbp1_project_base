package org.ucb.appp1.feature.catalog.data.mapper

import org.ucb.appp1.feature.catalog.data.dto.MovieDto
import org.ucb.appp1.feature.catalog.domain.model.MovieModel

fun MovieDto.toModel(): MovieModel {
    return MovieModel(
        title = this.title,
        posterPath = this.posterPath ?: ""
    )
}