package org.ucb.appp1.feature.remoteconfig.data.datasource

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.remoteconfig.remoteConfig
import kotlin.time.Duration.Companion.seconds
import org.ucb.appp1.core.util.safeCall
import org.ucb.appp1.feature.remoteconfig.data.dto.AppConfigDto

/** Nombres de los parámetros que se crean en la consola de Firebase (Remote Config). */
object RemoteConfigKeys {
    const val SUPPORT_PHONE = "support_phone"
    const val WEBSITE_URL = "website_url"
    const val ONBOARDING = "onboarding"
    const val UPDATE_REQUIRED = "update_required"
    const val STORE_URL = "store_url"
    const val PAYMENT_PROVIDER = "payment_provider"
}

private const val DEFAULT_ONBOARDING = """[
  {"title":{"en":"Welcome","es":"Bienvenido","fr":"Bienvenue"},"url":"","description":{"en":"Discover the app","es":"Descubre la aplicación","fr":"Découvrez l'application"}},
  {"title":{"en":"Stay informed","es":"Mantente informado","fr":"Restez informé"},"url":"https://www.ucb.edu.bo","description":{"en":"Real-time updates","es":"Actualizaciones en tiempo real","fr":"Mises à jour en temps réel"}}
]"""

class RemoteConfigDataSource {

    private var configured = false

    suspend fun fetch(): Result<AppConfigDto> = safeCall {
        val config = Firebase.remoteConfig

        if (!configured) {
            // 0 segundos = siempre pide los valores nuevos (útil para la demo).
            // En producción usa un intervalo mayor (por ejemplo 12.hours) para no ser limitado.
            config.settings { minimumFetchInterval = 0.seconds }
            // Valores por defecto: se usan si no hay internet o el parámetro no existe en la consola.
            config.setDefaults(
                RemoteConfigKeys.SUPPORT_PHONE to "70000000",
                RemoteConfigKeys.WEBSITE_URL to "https://www.ucb.edu.bo",
                RemoteConfigKeys.ONBOARDING to DEFAULT_ONBOARDING,
                RemoteConfigKeys.UPDATE_REQUIRED to false,
                RemoteConfigKeys.STORE_URL to "https://play.google.com/store",
                RemoteConfigKeys.PAYMENT_PROVIDER to "stripe"
            )
            configured = true
        }

        // Si falla la descarga (sin internet) seguimos con los valores guardados / por defecto.
        val fromServer = safeCall { config.fetchAndActivate() }.isSuccess

        AppConfigDto(
            supportPhone = config.getValue(RemoteConfigKeys.SUPPORT_PHONE).asString(),
            websiteUrl = config.getValue(RemoteConfigKeys.WEBSITE_URL).asString(),
            onboardingJson = config.getValue(RemoteConfigKeys.ONBOARDING).asString(),
            updateRequired = config.getValue(RemoteConfigKeys.UPDATE_REQUIRED).asBoolean(),
            storeUrl = config.getValue(RemoteConfigKeys.STORE_URL).asString(),
            paymentProvider = config.getValue(RemoteConfigKeys.PAYMENT_PROVIDER).asString(),
            fromServer = fromServer
        )
    }
}
