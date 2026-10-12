package org.ucb.appp1.feature.remoteconfig.domain.model

data class LocalizedText(val values: Map<String, String>) {
    fun resolve(language: String, fallback: String = "en"): String =
        values[language]?.takeIf { it.isNotBlank() }
            ?: values[fallback]?.takeIf { it.isNotBlank() }
            ?: values.values.firstOrNull { it.isNotBlank() }
            ?: ""
}

data class OnboardingItem(
    val title: LocalizedText,
    val description: LocalizedText,
    val url: String?
)

data class AppConfig(
    val supportPhone: String,
    val websiteUrl: String,
    val onboarding: List<OnboardingItem>,
    val updateRequired: Boolean,
    val storeUrl: String,
    val paymentProvider: String,
    /** true si los valores se descargaron de Firebase; false si son los valores por defecto/guardados. */
    val fromServer: Boolean
)
