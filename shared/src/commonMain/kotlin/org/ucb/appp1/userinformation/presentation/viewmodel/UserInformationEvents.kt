package org.ucb.appp1.userinformation.presentation.viewmodel

sealed interface UserInformationEvents {
    data class OnUsernameChanged(val username: String) : UserInformationEvents
    data object OnSearchClicked : UserInformationEvents
    data object OnBackClicked : UserInformationEvents
}
