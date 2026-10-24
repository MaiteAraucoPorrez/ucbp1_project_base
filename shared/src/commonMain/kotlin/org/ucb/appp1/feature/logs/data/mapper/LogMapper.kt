package org.ucb.appp1.feature.logs.data.mapper

import com.develoop.logs.ELogLevel
import com.develoop.logs.ELogResultCode
import com.develoop.logs.LogData
import com.develoop.logs.LogRequest
import com.develoop.logs.LogResponse
import okio.ByteString.Companion.encodeUtf8
import org.ucb.appp1.feature.logs.domain.model.LogEntry
import org.ucb.appp1.feature.logs.domain.model.LogLevel
import org.ucb.appp1.feature.logs.domain.model.LogSendResult

internal fun List<LogEntry>.toWireRequest(): LogRequest =
    LogRequest(logs = map { it.toWire() })

internal fun LogEntry.toWire(): LogData = LogData(
    androidId = androidId.encodeUtf8(),
    appInstanceId = appInstanceId.encodeUtf8(),
    logLevel = level.toWire(),
    message = message,
    stackTrace = stackTrace,
    serverTimeStamp = serverTimeStamp,
    mobileTimeStamp = mobileTimeStamp,
    versionCode = versionCode,
    userId = userId
)

internal fun LogResponse.toDomain(): LogSendResult = when (resultCode) {
    ELogResultCode.LOG_RESULT_OK -> LogSendResult.OK
    ELogResultCode.LOG_RESULT_ERROR -> LogSendResult.ERROR
    ELogResultCode.LOG_RESULT_UNDEFINED -> LogSendResult.UNDEFINED
}

private fun LogLevel.toWire(): ELogLevel = when (this) {
    LogLevel.UNKNOWN -> ELogLevel.LEVEL_UNKNOWN
    LogLevel.INFO -> ELogLevel.LEVEL_INFO
    LogLevel.DEBUG -> ELogLevel.LEVEL_DEBUG
    LogLevel.TRACE -> ELogLevel.LEVEL_TRACE
    LogLevel.WARN -> ELogLevel.LEVEL_WARN
    LogLevel.ERROR -> ELogLevel.LEVEL_ERROR
    LogLevel.FATAL -> ELogLevel.LEVEL_FATAL
}
