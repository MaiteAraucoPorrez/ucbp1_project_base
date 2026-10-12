package org.ucb.appp1.feature.announcements.presentation.viewmodel

sealed interface AnnouncementsEvents {
    data class OnAdd(val title: String, val message: String) : AnnouncementsEvents
    data class OnDelete(val id: String) : AnnouncementsEvents
}
