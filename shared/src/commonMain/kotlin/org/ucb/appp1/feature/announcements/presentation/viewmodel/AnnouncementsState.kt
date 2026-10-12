package org.ucb.appp1.feature.announcements.presentation.viewmodel

import org.ucb.appp1.feature.announcements.domain.model.Announcement

data class AnnouncementsState(
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val announcements: List<Announcement> = emptyList()
)
