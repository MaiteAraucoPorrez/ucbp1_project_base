package org.ucb.appp1.feature.pushnotifications.data.mapper

import org.ucb.appp1.feature.pushnotifications.data.dto.DeviceTokenDto
import org.ucb.appp1.feature.pushnotifications.domain.model.DeviceToken

fun DeviceTokenDto.toDomain() = DeviceToken(
    token = token,
    appId = appId,
    platform = platform,
    segment = segment,
    updatedAt = updatedAt
)
