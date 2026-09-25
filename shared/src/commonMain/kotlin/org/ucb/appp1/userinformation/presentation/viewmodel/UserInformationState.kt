package org.ucb.appp1.userinformation.presentation

import org.ucb.appp1.userinformation.domain.model.UserInfoModel

sealed interface UserInformationUiState {
    data object Idle : UserInformationUiState
    data object Loading : UserInformationUiState
    data class Success(val userInfo: UserInfoModel) : UserInformationUiState
    data class Error(val message: String) : UserInformationUiState
}
