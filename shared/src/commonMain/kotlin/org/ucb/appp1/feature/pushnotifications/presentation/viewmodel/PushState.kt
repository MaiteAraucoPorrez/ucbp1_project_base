package org.ucb.appp1.feature.pushnotifications.presentation.viewmodel

import org.ucb.appp1.feature.pushnotifications.domain.model.DeviceToken
import org.ucb.appp1.feature.pushnotifications.domain.model.PushSegment

data class PushState(
    val isLoading: Boolean = false,
    val message: String? = null,
    val currentToken: String? = null,
    val segment: PushSegment = PushSegment.PREPAID,
    val tokens: List<DeviceToken> = emptyList()
)
