package de.chrisbecker386.maintainer.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String,
    val icon: ImageVector? = null
) {
    object Home : Screen(route = "home_screen", title = "home", icon = Icons.Default.Home)
    object Info : Screen(route = "info_screen", title = "info", icon = Icons.Default.Info)
    object Settings :
        Screen(route = "settings_screen", title = "settings", icon = Icons.Default.Settings)
}

val APP_TABS = listOf(Screen.Home, Screen.Info, Screen.Settings)