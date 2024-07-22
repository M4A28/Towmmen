package com.mohmmed.mosa.eg.towmmen.presenter.nafgraph
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.mohmmed.mosa.eg.towmmen.presenter.navigator.AppNavigator

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NavGraph(
    startDestination: String
) {
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = startDestination ){

        navigation(
            route = Route.AppNavigation.route,
            startDestination = Route.AppNavigationScreen.route
        ){
            composable(
                route = Route.AppNavigationScreen.route
            ){
                AppNavigator()
            }
        }
    }
}

