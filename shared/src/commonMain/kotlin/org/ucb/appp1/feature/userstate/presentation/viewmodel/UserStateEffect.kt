package org.ucb.appp1.feature.userstate.presentation

sealed interface UserStateEffect {
    data object NavigateToLogin : UserStateEffect
    data object NavigateToUserSearch : UserStateEffect
}
