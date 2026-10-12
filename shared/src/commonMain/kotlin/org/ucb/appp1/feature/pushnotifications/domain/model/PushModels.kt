package org.ucb.appp1.feature.pushnotifications.domain.model

/**
 * Segmentos de usuarios. Cada segmento es un "topic" de FCM: desde la consola de Firebase
 * se puede enviar una notificación solo a un topic (pizarra: "Segmentar", prepago / postpago).
 */
enum class PushSegment(val topic: String, val label: String) {
    PREPAID("segment_prepaid", "Prepago"),
    POSTPAID("segment_postpaid", "Postpago")
}

data class DeviceToken(
    val token: String,
    val appId: String,
    val platform: String,
    val segment: String,
    val updatedAt: Long
)
