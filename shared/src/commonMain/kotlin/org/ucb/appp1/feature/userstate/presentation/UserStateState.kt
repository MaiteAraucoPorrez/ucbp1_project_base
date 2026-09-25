package org.ucb.appp1.feature.userstate.presentation

import org.ucb.appp1.core.domain.model.UserModel
import org.ucb.appp1.feature.movielist.domain.model.Movie

data class UserStateState(
    val isLoading: Boolean = false,
    val user: UserModel? = null,
    val favoriteMovies: List<Movie> = emptyList()
)
