package org.ucb.appp1.feature.remoteconfig.presentation.viewmodel

import org.ucb.appp1.feature.remoteconfig.domain.model.AppConfig

data class RemoteConfigState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val config: AppConfig? = null,
    val language: String = "es"
)
