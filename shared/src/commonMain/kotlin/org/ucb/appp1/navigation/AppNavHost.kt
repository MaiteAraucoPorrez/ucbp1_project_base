package org.ucb.appp1.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import org.ucb.appp1.feature.catalog.presentation.screen.CatalogScreen
import org.ucb.appp1.feature.login.presentation.screen.LoginScreen
import org.ucb.appp1.feature.moviedetail.presentation.screen.MovieDetailScreen
import org.ucb.appp1.feature.movielist.domain.vo.MovieId
import org.ucb.appp1.feature.movielist.presentation.screen.MovieListScreen
import org.ucb.appp1.feature.signup.presentation.screen.RegisterScreen
import org.ucb.appp1.feature.userstate.presentation.screen.UserStateEditScreen
import org.ucb.appp1.feature.userstate.presentation.screen.UserStateScreen
import org.ucb.appp1.userinformation.presentation.screen.UserInformationScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    //NavHost(navController = navController, startDestination = NavRoute.Login) {
    NavHost(navController = navController, startDestination = NavRoute.Catalog) {

        composable<NavRoute.Catalog> {
            CatalogScreen()
        }

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
                onNavigateToDetail = { movieId -> navController.navigate(NavRoute.MovieDetail(movieId.value)) },
                onNavigateToProfile = { navController.navigate(NavRoute.Profile) }
            )
        }

        composable<NavRoute.MovieDetail> { backStackEntry ->
            val route = backStackEntry.toRoute<NavRoute.MovieDetail>()
            MovieDetailScreen(
                movieId = MovieId(route.movieId),
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
                onNavigateToUserSearch = { navController.navigate(NavRoute.UserSearch) },
                onNavigateToEditProfile = { navController.navigate(NavRoute.ProfileEdit) }
            )
        }

        composable<NavRoute.ProfileEdit> {
            UserStateEditScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable<NavRoute.UserSearch> {
            UserInformationScreen(onNavigateBack = { navController.popBackStack() })
        }
    }
}
