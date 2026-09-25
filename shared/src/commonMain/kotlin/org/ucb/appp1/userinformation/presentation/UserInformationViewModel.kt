package org.ucb.appp1.userinformation.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.ucb.appp1.userinformation.domain.repository.GithubRepository

class UserInformationViewModel(
    private val repository: GithubRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UserInformationUiState>(UserInformationUiState.Idle)
    val uiState: StateFlow<UserInformationUiState> = _uiState.asStateFlow()

    fun fetchUser(username: String) {
        if (username.isBlank()) return
        viewModelScope.launch {
            _uiState.value = UserInformationUiState.Loading
            repository.findByAlias(username)
                .onSuccess { _uiState.value = UserInformationUiState.Success(it) }
                .onFailure { _uiState.value = UserInformationUiState.Error(it.message ?: "Error desconocido") }
        }
    }
}
