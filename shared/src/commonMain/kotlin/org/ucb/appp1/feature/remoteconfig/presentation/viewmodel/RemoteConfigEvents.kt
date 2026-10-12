package org.ucb.appp1.feature.remoteconfig.presentation.viewmodel

sealed interface RemoteConfigEvents {
    data object OnLoad : RemoteConfigEvents
    data object OnRefresh : RemoteConfigEvents
    data class OnLanguageSelected(val language: String) : RemoteConfigEvents
}
