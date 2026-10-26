package org.ucb.appp1.feature.logs.data.service

import com.develoop.logs.LogServiceClient
import org.ucb.appp1.core.util.safeCall
import org.ucb.appp1.feature.logs.data.config.LogsServerConfig
import org.ucb.appp1.feature.logs.data.datasource.LogsRemoteDataSource
import org.ucb.appp1.feature.logs.data.mapper.toDomain
import org.ucb.appp1.feature.logs.data.mapper.toWireRequest
import org.ucb.appp1.feature.logs.domain.model.LogEntry
import org.ucb.appp1.feature.logs.domain.model.LogSendResult

class LogsGrpcService(
    private val config: LogsServerConfig
) : LogsRemoteDataSource {

    private var cachedUrl: String? = null
    private var cachedClient: LogServiceClient? = null

    override suspend fun send(logs: List<LogEntry>): Result<LogSendResult> = safeCall {
        val client = clientFor(config.baseUrl.trim())
            ?: throw UnsupportedOperationException("gRPC no está disponible en esta plataforma (iOS)")
        client.send().execute(logs.toWireRequest()).toDomain()
    }

    // Reutiliza el cliente mientras la dirección no cambie.
    private fun clientFor(url: String): LogServiceClient? {
        if (url != cachedUrl) {
            cachedClient = createLogServiceClient(url)
            cachedUrl = url
        }
        return cachedClient
    }
}
