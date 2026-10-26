package org.ucb.appp1.userinformation.presentation.viewmodel

import org.ucb.appp1.userinformation.domain.model.UserInfoModel

data class UserInformationState(
    val username: String = "octocat",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userInfo: UserInfoModel? = null
)
