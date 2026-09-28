package org.ucb.appp1.feature.userstate.presentation.viewmodel

sealed interface UserStateEffect {
    data object NavigateToLogin : UserStateEffect
    data object NavigateToUserSearch : UserStateEffect
    data object NavigateToEditProfile : UserStateEffect
}
