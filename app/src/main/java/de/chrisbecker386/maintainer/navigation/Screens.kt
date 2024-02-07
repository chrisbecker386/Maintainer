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

object Overview : Screen {
    override val route: String = "overview_screen"
    override val title: String = "Overview"
    override val icon: ImageVector = Icons.Default.Home
}

object Info : Screen {
    override val route: String = "info_screen"
    override val title: String = "Info"
    override val icon: ImageVector = Icons.Default.Info
}

object Settings : Screen {
    override val route: String = "settings_screen"
    override val title: String = "Settings"
    override val icon: ImageVector = Icons.Default.Settings
}

object SingleSection : Screen {
    override val route: String = "single_section"
    override val title: String = "Section"
    override val icon = null
    const val sectionTypeArg = "section_type"
    val routeWithArgs = "$route/{$sectionTypeArg}"
    val arguments = listOf(navArgument(sectionTypeArg) { type = NavType.IntType })
}

object SingleMachine : Screen {
    override val route: String = "single_machine"
    override val title: String = "Machine"
    override val icon = null
    const val machineTypeArg = "machine_type"
    val routeWithArgs = "$route/{$machineTypeArg}"
    val arguments = listOf(navArgument(machineTypeArg) { type = NavType.IntType })
}

object SingleTask : Screen {
    override val route: String = "single_task"
    override val title: String = "Task"
    override val icon = null
    const val taskTypeArg = "task_type"
    val routeWithArgs = "$route/{$taskTypeArg}"
    val arguments = listOf(navArgument(taskTypeArg) { type = NavType.IntType })
}

object SectionCreation : Screen {
    override val route: String = "section_creation_screen"
    override val title: String = "SectionCreation"
    override val icon = null
    const val sectionIdTypeArg = "section_id"
    val routeWithArgs =
        "$route?id={$sectionIdTypeArg}"
    val arguments =
        listOf(
            navArgument(sectionIdTypeArg) {
                defaultValue = 0
                type = NavType.IntType
            }
        )
}

object MachineCreation : Screen {
    override val route: String = "machine_creation_screen"
    override val title: String = "MachineCreation"
    override val icon = null
    const val machineIdTypeArg = "machine_id"
    const val machineForeignIdTypeArg = "machine_foreign_id"
    val routeWithArgs =
        "$route?machine_id={$machineIdTypeArg}&machine_foreign_id={$machineForeignIdTypeArg}"
    val arguments = listOf(
        navArgument(machineIdTypeArg) {
            defaultValue = 0
            type = NavType.IntType
        },
        navArgument(machineForeignIdTypeArg) {
            type = NavType.IntType
        }
    )
}

object TaskCreation : Screen {
    override val route: String = "task_creation_screen"
    override val title: String = "TaskCreation"
    override val icon: ImageVector? = null
    const val taskIdTypeArg = "task_id"
    const val taskForeignIdTypeArg = "task_foreign_id"
    val routeWithArgs = "$route?task_id={$taskIdTypeArg}&task_foreign_id={$taskForeignIdTypeArg}"
    val arguments = listOf(
        navArgument(taskIdTypeArg) {
            defaultValue = 0
            type = NavType.IntType
        },
        navArgument(taskForeignIdTypeArg) {
            type = NavType.IntType
        }
    )
}

val APP_SCREENS = listOf(
    Overview,
    Settings,
    SingleSection,
    SingleMachine,
    SingleTask,
    SectionCreation,
    MachineCreation,
    TaskCreation
)
