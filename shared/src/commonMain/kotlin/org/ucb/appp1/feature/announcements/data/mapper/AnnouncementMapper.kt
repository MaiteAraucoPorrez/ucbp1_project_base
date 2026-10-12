package org.ucb.appp1.feature.announcements.data.mapper

import org.ucb.appp1.feature.announcements.data.dto.AnnouncementDto
import org.ucb.appp1.feature.announcements.domain.model.Announcement

fun AnnouncementDto.toDomain(id: String) = Announcement(
    id = id,
    title = title,
    message = message,
    createdAt = createdAt
)
