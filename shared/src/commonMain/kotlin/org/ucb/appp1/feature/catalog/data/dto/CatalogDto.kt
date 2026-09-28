package org.ucb.appp1.feature.catalog.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CatalogDto(
    val page: Int,
    val results: List<MovieDto>
)