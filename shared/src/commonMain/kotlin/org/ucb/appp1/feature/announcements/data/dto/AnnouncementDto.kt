package org.ucb.appp1.feature.announcements.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class AnnouncementDto(
    val title: String = "",
    val message: String = "",
    val createdAt: Long = 0L
)
