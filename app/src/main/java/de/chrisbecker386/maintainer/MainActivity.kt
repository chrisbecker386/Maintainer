package de.chrisbecker386.maintainer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import de.chrisbecker386.maintainer.navigation.*
import de.chrisbecker386.maintainer.ui.theme.APP_BAR_HEIGHT
import de.chrisbecker386.maintainer.ui.theme.BOTTOM_BAR_HEIGHT
import de.chrisbecker386.maintainer.ui.theme.MaintainerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaintainerApp()
        }
    }

    @Composable
    fun MaintainerApp() {
        MaintainerTheme {
            val navController = rememberNavController()
            val currentBackStack by navController.currentBackStackEntryAsState()
            val currentDestination = currentBackStack?.destination
            val currentScreen =
                APP_TABS.find { it.route == currentDestination?.route } ?: Screen.Home

            Scaffold(
                topBar = {
                    MaintainerAppBar(
                        title = currentScreen.title,
//                        showBackButton = currentScreen == Screen.Home,
//                        showContextMenu = currentScreen == Screen.Settings,
                        modifier = Modifier.height(APP_BAR_HEIGHT)
                    )
                },
                bottomBar = {
                    MaintainerBottomBar(
                        allTabScreens = APP_TABS,
                        currentTab = currentScreen,
                        onSelected = { newDestination ->
                            navController.navigateWithPopUp(
                                newDestination.route
                            )
                        },
                        modifier = Modifier.height(BOTTOM_BAR_HEIGHT)
                    )
                }
            )
            { innerPadding ->
                MaintainerNavGraph(
                    navController = navController,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}
