package org.ucb.appp1.feature.logs.domain.repository

import org.ucb.appp1.feature.logs.domain.model.LogEntry
import org.ucb.appp1.feature.logs.domain.model.LogSendResult

interface LogsRepository {
    suspend fun sendLogs(logs: List<LogEntry>): Result<LogSendResult>
}
