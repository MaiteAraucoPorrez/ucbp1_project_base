package org.ucb.appp1.feature.logs.presentation.viewmodel

data class LogsState(
    val serverUrl: String = "",
    val isLoading: Boolean = false,
    val message: String? = null,
    val isError: Boolean = false
)
