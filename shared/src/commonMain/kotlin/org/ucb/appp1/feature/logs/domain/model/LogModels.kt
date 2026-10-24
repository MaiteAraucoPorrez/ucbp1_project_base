package org.ucb.appp1.feature.logs.domain.model

enum class LogLevel { UNKNOWN, INFO, DEBUG, TRACE, WARN, ERROR, FATAL }

enum class LogSendResult { UNDEFINED, ERROR, OK }

data class LogEntry(
    val androidId: String,
    val appInstanceId: String,
    val level: LogLevel,
    val message: String,
    val stackTrace: String,
    val serverTimeStamp: Long,
    val mobileTimeStamp: Long,
    val versionCode: Int,
    val userId: String
)
