package org.ucb.appp1.feature.remoteconfig.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class OnboardingItemDto(
    val title: Map<String, String> = emptyMap(),
    val url: String? = null,
    val description: Map<String, String> = emptyMap()
)

data class AppConfigDto(
    val supportPhone: String,
    val websiteUrl: String,
    val onboardingJson: String,
    val updateRequired: Boolean,
    val storeUrl: String,
    val paymentProvider: String,
    val fromServer: Boolean
)
