package org.ucb.appp1.feature.logs.data.config

/**
 * Dirección del servidor gRPC. Se puede cambiar desde la pantalla, así no hay que recompilar
 * cuando ngrok te da una dirección nueva.
 *
 *  - Emulador de Android:   http://10.0.2.2:9090
 *  - Celular real / ngrok:  https://xxxx.ngrok-free.dev
 */
class LogsServerConfig {
    var baseUrl: String = DEFAULT_BASE_URL

    companion object {
        const val DEFAULT_BASE_URL = "http://10.0.2.2:9090"
    }
}
