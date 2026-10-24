package org.ucb.appp1.feature.logs.data.datasource

import org.ucb.appp1.feature.logs.domain.model.LogEntry
import org.ucb.appp1.feature.logs.domain.model.LogSendResult

interface LogsRemoteDataSource {
    suspend fun send(logs: List<LogEntry>): Result<LogSendResult>
}
