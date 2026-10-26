package org.ucb.appp1.feature.remoteconfig.data.mapper

import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import org.ucb.appp1.feature.remoteconfig.data.dto.AppConfigDto
import org.ucb.appp1.feature.remoteconfig.data.dto.OnboardingItemDto
import org.ucb.appp1.feature.remoteconfig.domain.model.AppConfig
import org.ucb.appp1.feature.remoteconfig.domain.model.LocalizedText
import org.ucb.appp1.feature.remoteconfig.domain.model.OnboardingItem

private val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
}

fun AppConfigDto.toDomain(): AppConfig = AppConfig(
    supportPhone = supportPhone,
    websiteUrl = websiteUrl,
    onboarding = parseOnboarding(onboardingJson),
    updateRequired = updateRequired,
    storeUrl = storeUrl,
    paymentProvider = paymentProvider,
    fromServer = fromServer
)

private fun parseOnboarding(raw: String): List<OnboardingItem> {
    return try {
        json.decodeFromString<List<OnboardingItemDto>>(raw).map { dto ->
            OnboardingItem(
                title = LocalizedText(dto.title),
                description = LocalizedText(dto.description),
                url = dto.url?.takeIf { it.isNotBlank() }
            )
        }
    } catch (ignored: SerializationException) {
        // JSON mal formado en Remote Config: se muestra sin onboarding en vez de fallar.
        emptyList()
    } catch (ignored: IllegalArgumentException) {
        emptyList()
    }
}
