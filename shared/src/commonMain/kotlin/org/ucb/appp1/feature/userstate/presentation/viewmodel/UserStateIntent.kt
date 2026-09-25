package org.ucb.appp1.feature.userstate.presentation

sealed interface UserStateIntent {
    data object LoadProfile : UserStateIntent
    data object OnLogoutClicked : UserStateIntent
    data object OnSearchGithubClicked : UserStateIntent
}
