package de.chrisbecker386.maintainer.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import de.chrisbecker386.maintainer.ui.home.HomeScreen
import de.chrisbecker386.maintainer.ui.info.InfoScreen
import de.chrisbecker386.maintainer.ui.settings.SettingsScreen

@Composable
fun MaintainerNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen()
        }
        composable(route = Screen.Info.route) {
            InfoScreen()
        }
        composable(route = Screen.Settings.route) {
            SettingsScreen()
        }
    }
}

fun NavHostController.navigateWithPopUp(route: String) =
    this.navigate(route) {
        this@navigateWithPopUp.graph.findStartDestination().id
        popUpTo(route) {
            saveState = true
        }
        launchSingleTop = true
    }
