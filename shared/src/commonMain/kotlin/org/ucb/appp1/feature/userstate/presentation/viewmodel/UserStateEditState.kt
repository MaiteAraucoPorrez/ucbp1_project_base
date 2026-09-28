package org.ucb.appp1.feature.userstate.presentation.viewmodel

/**
 * MVVM (como Registro y Movie Detail): sin Intent/Effect, funciones directas.
 */
data class UserStateEditState(
    val fullName: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSaved: Boolean = false
)
