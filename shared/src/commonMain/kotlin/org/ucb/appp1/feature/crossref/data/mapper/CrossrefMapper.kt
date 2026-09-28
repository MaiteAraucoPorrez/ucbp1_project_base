package org.ucb.appp1.feature.crossref.data.mapper

import org.ucb.appp1.feature.crossref.data.dto.ItemDto
import org.ucb.appp1.feature.crossref.domain.model.CrossrefModel

fun ItemDto.toModel(): CrossrefModel = CrossrefModel(
    doi = doi,
    title = title.firstOrNull() ?: "Sin titulo",
    author = author
        ?.joinToString(", ") { "${it.given.orEmpty()} ${it.family.orEmpty()}".trim() }
        ?.ifBlank { "Autor desconocido" }
        ?: "Autor desconocido",
    published = published?.dateParts?.firstOrNull()?.joinToString("-") ?: "Fecha desconocida",
    type = type,
    url = url,
    containerTitle = containerTitle?.firstOrNull() ?: "Sin revista"
)
