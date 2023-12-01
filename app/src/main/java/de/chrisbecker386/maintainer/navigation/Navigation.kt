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

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import de.chrisbecker386.maintainer.ui.tab.home.creation.CreationScreen
import de.chrisbecker386.maintainer.ui.tab.home.home.OverviewScreen
import de.chrisbecker386.maintainer.ui.tab.home.machine.SingleMachineScreen
import de.chrisbecker386.maintainer.ui.tab.home.section.SingleSectionScreen
import de.chrisbecker386.maintainer.ui.tab.home.task.SingleTaskScreen
import de.chrisbecker386.maintainer.ui.tab.info.InfoScreen
import de.chrisbecker386.maintainer.ui.tab.settings.SettingsScreen

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
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
            OverviewScreen(
                onSectionClick = { careType -> navController.navigateToSingleSection(careType) },
                onMachineClick = { machineType ->
                    navController.navigateToSingleMachine(machineType)
                },
                onCreationClick = { creationType, id ->
                    navController.navigateToCreation(creationType, id)
                }

            )
        }
        composable(route = Info.route) {
            InfoScreen()
        }
        composable(route = Settings.route) {
            SettingsScreen()
        }

        composable(
            route = SingleSection.routeWithArgs,
            arguments = SingleSection.arguments
        ) { navBackStackEntry ->
            val sectionType = navBackStackEntry.arguments?.getInt(SingleSection.sectionTypeArg)

            SingleSectionScreen(
                sectionType = sectionType,
                onMachineClick = { machineType -> navController.navigateToSingleMachine(machineType) }
            )
        }

        composable(
            route = SingleMachine.routeWithArgs,
            arguments = SingleMachine.arguments
        ) { navBackStackEntry ->
            val machineType = navBackStackEntry.arguments?.getInt(SingleMachine.machineTypeArg)
            if (machineType != null) {
                SingleMachineScreen(
                    machineType = machineType,
                    onTaskClick = { taskType ->
                        navController.navigateToSingleTask(taskType)
                    }
                )
            }
        }

        composable(
            route = SingleTask.routeWithArgs,
            arguments = SingleTask.arguments
        ) { navBackStackEntry ->
            val taskType = navBackStackEntry.arguments?.getInt(SingleTask.taskTypeArg)
            if (taskType != null) {
                SingleTaskScreen(
                    taskType = taskType,
                    navigateUp = { navController.navigateUp() }
                )
            }
        }

        composable(
            route = Creation.routeWithArgs,
            arguments = Creation.arguments
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getInt(Creation.creationIdTypeArg)
            val creationType = navBackStackEntry.arguments?.getSerializable(
                Creation.creationTypeArg,
                CreationType::class.java
            )
            creationType?.let {
                if (id != null) {
                    CreationScreen(
                        id = id,
                        creationType = creationType,
                        navigateUp = { navController.navigateUp() }
                    )
                } else {
                    CreationScreen(
                        creationType = creationType,
                        navigateUp = { navController.navigateUp() }
                    )
                }
            }
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

private fun NavHostController.navigateToSingleMachine(machineType: Int) {
    this.navigate("${SingleMachine.route}/$machineType")
}

private fun NavHostController.navigateToSingleTask(taskType: Int) {
    this.navigate("${SingleTask.route}/$taskType")
}

private fun NavHostController.navigateToSingleSection(sectionType: Int) {
    this.navigate("${SingleSection.route}/$sectionType")
}

private fun NavHostController.navigateToCreation(creationType: CreationType, id: Int?) {
    this.navigate("${Creation.route}?creation_id_type=$id&creationTypeArg=$creationType")
}
