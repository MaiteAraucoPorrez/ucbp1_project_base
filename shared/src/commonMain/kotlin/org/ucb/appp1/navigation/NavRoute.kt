package org.ucb.appp1.navigation

import kotlinx.serialization.Serializable

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
    data object ProfileEdit : NavRoute()

    @Serializable
    data object UserSearch : NavRoute()

    @Serializable
    data object Catalog : NavRoute()

    @Serializable
    data object Crossref : NavRoute()
}
