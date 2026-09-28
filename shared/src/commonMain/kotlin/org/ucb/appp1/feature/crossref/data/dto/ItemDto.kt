package org.ucb.appp1.feature.crossref.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ItemDto(
    @SerialName("DOI")
    val doi: String = "",
    val title: List<String> = emptyList(),
    val author: List<AuthorDto>? = null,
    val published: PublishedDto? = null,
    val type: String = "",
    @SerialName("URL")
    val url: String = "",
    @SerialName("container-title")
    val containerTitle: List<String>? = null
)

@Serializable
data class AuthorDto(
    val given: String? = null,
    val family: String? = null
)

@Serializable
data class PublishedDto(
    @SerialName("date-parts")
    val dateParts: List<List<Int?>>? = null
)