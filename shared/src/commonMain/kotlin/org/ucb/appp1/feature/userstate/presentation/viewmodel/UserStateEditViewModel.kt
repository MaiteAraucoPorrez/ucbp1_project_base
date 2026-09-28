package org.ucb.appp1.feature.userstate.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ucb.appp1.feature.userstate.domain.usecase.GetCurrentUserUseCase
import org.ucb.appp1.feature.userstate.domain.usecase.UpdateProfileUseCase

class UserStateEditViewModel(
    private val getCurrentUser: GetCurrentUserUseCase,
    private val updateProfile: UpdateProfileUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UserStateEditState())
    val state: StateFlow<UserStateEditState> = _state.asStateFlow()

    init {
        // Precarga el nombre actual para que la usuaria no empiece de un campo vacío.
        getCurrentUser()?.let { user ->
            _state.update { it.copy(fullName = user.fullName) }
        }
    }

    fun onFullNameChanged(value: String) = _state.update { it.copy(fullName = value, errorMessage = null) }

    fun onSaveClicked() {
        val current = _state.value
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            updateProfile(current.fullName)
                .onSuccess { _state.update { it.copy(isLoading = false, isSaved = true) } }
                .onFailure { error ->
                    _state.update {
                        it.copy(isLoading = false, errorMessage = error.message ?: "No se pudo guardar")
                    }
                }
        }
    }
}
