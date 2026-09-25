package org.ucb.appp1.feature.movielist.domain.model

data class Movie(
    val id: String,
    val title: String,
    val posterUrl: String,
    val genres: List<String>,
    val rating: Double,
    val isFavorite: Boolean,
    val synopsis: String = ""
)
