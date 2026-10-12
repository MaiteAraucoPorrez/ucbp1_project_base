package org.ucb.appp1.feature.pushnotifications.presentation.viewmodel

import org.ucb.appp1.feature.pushnotifications.domain.model.PushSegment

sealed interface PushEvents {
    data object OnGetToken : PushEvents
    data object OnRegisterToken : PushEvents
    data object OnDeleteToken : PushEvents
    data class OnSegmentSelected(val segment: PushSegment) : PushEvents
}
