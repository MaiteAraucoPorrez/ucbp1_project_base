package org.ucb.appp1.feature.logs.data.repository

import org.ucb.appp1.feature.logs.data.datasource.LogsRemoteDataSource
import org.ucb.appp1.feature.logs.domain.model.LogEntry
import org.ucb.appp1.feature.logs.domain.model.LogSendResult
import org.ucb.appp1.feature.logs.domain.repository.LogsRepository

class LogsRepositoryImpl(
    private val remoteDataSource: LogsRemoteDataSource
) : LogsRepository {

    override suspend fun sendLogs(logs: List<LogEntry>): Result<LogSendResult> =
        remoteDataSource.send(logs)
}
