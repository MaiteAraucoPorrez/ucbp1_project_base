package org.ucb.appp1.feature.logs.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ucb.appp1.core.util.currentTimeMillis
import org.ucb.appp1.feature.logs.data.config.LogsServerConfig
import org.ucb.appp1.feature.logs.domain.model.LogEntry
import org.ucb.appp1.feature.logs.domain.model.LogLevel
import org.ucb.appp1.feature.logs.domain.model.LogSendResult
import org.ucb.appp1.feature.logs.domain.usecase.SendLogsUseCase

class LogsViewModel(
    private val sendLogs: SendLogsUseCase,
    private val config: LogsServerConfig
) : ViewModel() {

    private val _state = MutableStateFlow(LogsState(serverUrl = config.baseUrl))
    val state = _state.asStateFlow()

    fun emitEvent(event: LogsEvents) {
        when (event) {
            is LogsEvents.OnUrlChanged -> _state.update { it.copy(serverUrl = event.url) }
            LogsEvents.OnSendTestLog -> sendTestLog()
        }
    }

    private fun sendTestLog() {
        config.baseUrl = _state.value.serverUrl
        val now = currentTimeMillis()
        val log = LogEntry(
            androidId = "android-demo-id",
            appInstanceId = "ucb-appp1",
            level = LogLevel.ERROR,
            message = "Prueba de log desde la app KMP",
            stackTrace = "sin stacktrace",
            serverTimeStamp = now,
            mobileTimeStamp = now,
            versionCode = 1,
            userId = "user_demo"
        )

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, message = null, isError = false) }
            sendLogs(listOf(log)).fold(
                onSuccess = { result ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            message = "Respuesta del servidor: $result",
                            isError = result != LogSendResult.OK
                        )
                    }
                },
                onFailure = { e ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            message = "Error: ${e.message ?: e::class.simpleName}",
                            isError = true
                        )
                    }
                }
            )
        }
    }
}
