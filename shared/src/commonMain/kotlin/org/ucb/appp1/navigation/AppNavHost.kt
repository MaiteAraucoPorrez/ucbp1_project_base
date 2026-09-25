package org.ucb.appp1.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.ucb.appp1.feature.login.presentation.LoginScreen
import org.ucb.appp1.feature.movielist.presentation.MovieListScreen
import org.ucb.appp1.feature.signup.presentation.RegisterScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavRoute.Login) {

        composable<NavRoute.Login> {
            LoginScreen(
                onNavigateToHome = {
                    navController.navigate(NavRoute.MovieList) {
                        popUpTo<NavRoute.Login> { inclusive = true }
                    }
                },
                onNavigateToSignUp = { navController.navigate(NavRoute.SignUp) }
            )
        }

        composable<NavRoute.SignUp> {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(NavRoute.MovieList) {
                        popUpTo<NavRoute.Login> { inclusive = true }
                    }
                },
                onNavigateToLogin = { navController.popBackStack() }
            )
        }

        composable<NavRoute.MovieList> {
            MovieListScreen(
                onNavigateToDetail = { movieId ->
                    navController.navigate(NavRoute.MovieDetail(movieId))
                }
            )
        }

        // TODO: cuando agreguemos MovieDetail, Perfil y UserInformation,
        // se agrega un composable<NavRoute.X> { ... } por cada uno, igual que arriba.
    }
}
