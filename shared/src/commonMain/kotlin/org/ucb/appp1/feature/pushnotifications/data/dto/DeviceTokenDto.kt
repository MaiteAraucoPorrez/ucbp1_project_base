package org.ucb.appp1.feature.pushnotifications.data.dto

import kotlinx.serialization.Serializable

/** Documento de la colección "device_tokens" en Firestore (el id del documento es el token). */
@Serializable
data class DeviceTokenDto(
    val token: String = "",
    val appId: String = "",
    val platform: String = "",
    val segment: String = "",
    val updatedAt: Long = 0L
)
