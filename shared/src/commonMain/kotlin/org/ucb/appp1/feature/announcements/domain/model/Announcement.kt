package org.ucb.appp1.feature.announcements.domain.model

data class Announcement(
    val id: String,
    val title: String,
    val message: String,
    val createdAt: Long
)
