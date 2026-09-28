package org.ucb.appp1.feature.crossref.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CrossrefDto(
    val message: CrossrefMessageDto
)

@Serializable
data class CrossrefMessageDto(
    val items: List<ItemDto> = emptyList()
)
