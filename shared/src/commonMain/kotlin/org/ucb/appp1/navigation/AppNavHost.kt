package org.ucb.appp1.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import org.ucb.appp1.feature.login.presentation.LoginScreen
import org.ucb.appp1.feature.moviedetail.presentation.MovieDetailScreen
import org.ucb.appp1.feature.movielist.presentation.MovieListScreen
import org.ucb.appp1.feature.signup.presentation.RegisterScreen
import org.ucb.appp1.feature.userstate.presentation.UserStateScreen
import org.ucb.appp1.userinformation.presentation.UserInformationScreen

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
                onNavigateToDetail = { movieId -> navController.navigate(NavRoute.MovieDetail(movieId)) },
                onNavigateToProfile = { navController.navigate(NavRoute.Profile) }
            )
        }

        composable<NavRoute.MovieDetail> { backStackEntry ->
            val route = backStackEntry.toRoute<NavRoute.MovieDetail>()
            MovieDetailScreen(
                movieId = route.movieId,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable<NavRoute.Profile> {
            UserStateScreen(
                onNavigateToLogin = {
                    navController.navigate(NavRoute.Login) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onNavigateToUserSearch = { navController.navigate(NavRoute.UserSearch) }
            )
        }

        composable<NavRoute.UserSearch> {
            UserInformationScreen(onNavigateBack = { navController.popBackStack() })
        }
    }
}
