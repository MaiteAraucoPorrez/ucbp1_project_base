package org.ucb.appp1.feature.userstate.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ucb.appp1.feature.userstate.domain.usecase.GetCurrentUserUseCase
import org.ucb.appp1.feature.userstate.domain.usecase.GetFavoriteMoviesUseCase
import org.ucb.appp1.feature.userstate.domain.usecase.LogoutUseCase

/**
 * VIEWMODEL/REDUCER del diagrama de Perfil (MVI), igual patrón que Login.
 */
class UserStateViewModel(
    private val getCurrentUser: GetCurrentUserUseCase,
    private val getFavoriteMovies: GetFavoriteMoviesUseCase,
    private val logout: LogoutUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UserStateState())
    val state: StateFlow<UserStateState> = _state.asStateFlow()

    private val _effect = Channel<UserStateEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    init {
        onIntent(UserStateIntent.LoadProfile)
    }

    fun onIntent(intent: UserStateIntent) {
        when (intent) {
            UserStateIntent.LoadProfile -> loadProfile()
            UserStateIntent.OnLogoutClicked -> onLogoutClicked()
            UserStateIntent.OnSearchGithubClicked -> sendEffect(UserStateEffect.NavigateToUserSearch)
        }
    }

    private fun loadProfile() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val user = getCurrentUser()
            val favorites = getFavoriteMovies()
            _state.update { it.copy(isLoading = false, user = user, favoriteMovies = favorites) }
        }
    }

    private fun onLogoutClicked() {
        logout()
        sendEffect(UserStateEffect.NavigateToLogin)
    }

    private fun sendEffect(effect: UserStateEffect) {
        viewModelScope.launch { _effect.send(effect) }
    }
}
