package org.ucb.appp1.userinformation.presentation.viewmodel

sealed interface UserInformationEffects {
    data class ShowToast(val message: String) : UserInformationEffects
    data object NavigateBack : UserInformationEffects
}
