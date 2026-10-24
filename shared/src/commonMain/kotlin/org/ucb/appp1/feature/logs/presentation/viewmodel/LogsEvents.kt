package org.ucb.appp1.feature.logs.presentation.viewmodel

sealed interface LogsEvents {
    data class OnUrlChanged(val url: String) : LogsEvents
    data object OnSendTestLog : LogsEvents
}
