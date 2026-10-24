package org.ucb.appp1.feature.logs.domain.usecase

import org.ucb.appp1.feature.logs.domain.model.LogEntry
import org.ucb.appp1.feature.logs.domain.model.LogSendResult
import org.ucb.appp1.feature.logs.domain.repository.LogsRepository

class SendLogsUseCase(
    private val repository: LogsRepository
) {
    suspend operator fun invoke(logs: List<LogEntry>): Result<LogSendResult> =
        repository.sendLogs(logs)
}
