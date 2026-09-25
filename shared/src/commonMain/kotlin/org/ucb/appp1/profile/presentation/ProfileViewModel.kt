package org.ucb.appp1.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.ucb.appp1.profile.domain.model.UserInfoModel
import org.ucb.appp1.profile.domain.repository.GithubRepository

sealed interface ProfileUiState {
    data object Idle : ProfileUiState
    data object Loading : ProfileUiState
    data class Success(val userInfo: UserInfoModel) : ProfileUiState
    data class Error(val message: String) : ProfileUiState
}

class ProfileViewModel(
    private val repository: GithubRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Idle)
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun fetchUser(username: String) {
        if (username.isBlank()) return
        viewModelScope.launch {
            _uiState.value = ProfileUiState.Loading
            repository.findByAlias(username)
                .onSuccess { user ->
                    _uiState.value = ProfileUiState.Success(user)
                }
                .onFailure { error ->
                    _uiState.value = ProfileUiState.Error(error.message ?: "Error desconocido")
                }
        }
    }
}
