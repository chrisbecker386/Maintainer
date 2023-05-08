/*
 * Created by Christopher Becker on 13/04/2023, 17:40
 * Copyright (c) 2023. All rights reserved.
 * Last modified 13/04/2023, 17:40
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package de.chrisbecker386.maintainer.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument

interface Screen {
    val route: String
    val title: String
    val icon: ImageVector?
}

object Home : Screen {
    override val route: String = "home_screen"
    override val title: String = "home"
    override val icon: ImageVector = Icons.Default.Home
}

object Info : Screen {
    override val route: String = "info_screen"
    override val title: String = "info"
    override val icon: ImageVector = Icons.Default.Info
}

object Settings : Screen {
    override val route: String = "settings_screen"
    override val title: String = "settings"
    override val icon: ImageVector = Icons.Default.Settings
}

object SingleMachine : Screen {
    override val route: String = "single_machine"
    override val title: String = "Machine"
    override val icon = null
    const val machineTypeArg = "machine_type"
    val routeWithArgs = "$route/{$machineTypeArg}"
    val arguments = listOf(navArgument(machineTypeArg) { type = NavType.StringType })
}

object SingleTask : Screen {
    override val route: String = "single_task"
    override val title: String = "Task"
    override val icon = null
    const val taskTypeArg = "task_type"
    val routeWithArgs = "$route/{$taskTypeArg}"
    val arguments = listOf(navArgument(taskTypeArg) { type = NavType.StringType })
}

val APP_TABS = listOf(Home, Info, Settings)
