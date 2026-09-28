package org.ucb.appp1.feature.crossref.domain.model

data class CrossrefModel(
    val doi: String,
    val title: String,
    val author: String,
    val published: String,
    val type: String,
    val url: String,
    val containerTitle: String
)
