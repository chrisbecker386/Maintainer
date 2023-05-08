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

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import de.chrisbecker386.maintainer.ui.home.HomeScreen
import de.chrisbecker386.maintainer.ui.home.SingleMachineScreen
import de.chrisbecker386.maintainer.ui.home.SingleTaskScreen
import de.chrisbecker386.maintainer.ui.info.InfoScreen
import de.chrisbecker386.maintainer.ui.settings.SettingsScreen

@Composable
fun MaintainerNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Home.route,
        modifier = modifier
    ) {
        composable(route = Home.route) {
            HomeScreen(onMachineClick = { machineType ->
                navController.navigateToSingleMachine(machineType)
            })
        }
        composable(route = Info.route) {
            InfoScreen()
        }
        composable(route = Settings.route) {
            SettingsScreen()
        }
        composable(
            route = SingleMachine.routeWithArgs,
            arguments = SingleMachine.arguments
        ) { navBackStackEntry ->
            val machineType = navBackStackEntry.arguments?.getString(SingleMachine.machineTypeArg)
            SingleMachineScreen(
                machineType = machineType,
                onTaskClick = { taskType ->
                    navController.navigateToSingleTask(taskType)
                }
            )
        }
        composable(
            route = SingleTask.routeWithArgs,
            arguments = SingleTask.arguments
        ) { navBackStackEntry ->
            val taskType = navBackStackEntry.arguments?.getString(SingleTask.taskTypeArg)
            SingleTaskScreen(taskType)
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }

private fun NavHostController.navigateToSingleMachine(machineType: String) {
    this.navigateSingleTopTo("${SingleMachine.route}/$machineType")
}

private fun NavHostController.navigateToSingleTask(taskType: String) {
    this.navigate("${SingleTask.route}/$taskType")
}
