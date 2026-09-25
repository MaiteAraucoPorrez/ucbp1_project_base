package org.ucb.appp1.navigation

import kotlinx.serialization.Serializable

/**
 * Rutas de navegación tipadas (Navigation Compose Multiplatform).
 * Cada pantalla es un objeto o data class @Serializable.
 */
@Serializable
sealed class NavRoute {

    @Serializable
    data object Login : NavRoute()

    @Serializable
    data object SignUp : NavRoute()

    @Serializable
    data object MovieList : NavRoute()

    @Serializable
    data class MovieDetail(val movieId: String) : NavRoute()

    @Serializable
    data object Profile : NavRoute()

    @Serializable
    data object UserSearch : NavRoute()
}
