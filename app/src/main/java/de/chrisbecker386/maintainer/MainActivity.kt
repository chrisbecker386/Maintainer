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

package de.chrisbecker386.maintainer

import android.os.Bundle
import android.util.Log
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
import dagger.hilt.android.AndroidEntryPoint
import de.chrisbecker386.maintainer.navigation.APP_SCREENS
import de.chrisbecker386.maintainer.navigation.MaintainerAppBar
import de.chrisbecker386.maintainer.navigation.MaintainerNavGraph
import de.chrisbecker386.maintainer.navigation.Overview
import de.chrisbecker386.maintainer.navigation.SCREENS_WITH_ADD
import de.chrisbecker386.maintainer.navigation.SingleMachine
import de.chrisbecker386.maintainer.navigation.SingleSection
import de.chrisbecker386.maintainer.navigation.navigateToMachineCreation
import de.chrisbecker386.maintainer.navigation.navigateToSectionCreation
import de.chrisbecker386.maintainer.navigation.navigateToSettings
import de.chrisbecker386.maintainer.navigation.navigateToTaskCreation
import de.chrisbecker386.maintainer.ui.theme.DIM_XXL
import de.chrisbecker386.maintainer.ui.theme.MaintainerTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaintainersApp()
        }
    }

    @Composable
    fun MaintainersApp() {
        MaintainerTheme {
            val tag = "MaintainersApp"
            val navController = rememberNavController()
            val currentBackStack by navController.currentBackStackEntryAsState()
            val currentDestination = currentBackStack?.destination
            val currentScreen =
                APP_SCREENS.find {
                    // route without Args
                    (it.route == currentDestination?.route) ||
                        // route with Args
                        (it.route == currentDestination?.route?.split("/")?.first()) ||
                        (it.route == currentDestination?.route?.split("?")?.first())
                } ?: Overview

            Log.d(tag, currentDestination?.route.toString())

            val addScreen = SCREENS_WITH_ADD.firstOrNull { screen -> screen == currentScreen }

            fun navigateToCreation() =
                when (addScreen) {
                    is Overview -> {
                        navController.navigateToSectionCreation(null)
                    }

                    is SingleSection -> {
                        currentDestination?.id?.let {
                            navController.navigateToMachineCreation(null, it)
                        }
                    }

                    is SingleMachine -> {
                        currentDestination?.id?.let {
                            navController.navigateToTaskCreation(null, it)
                        }
                    }

                    else -> {}
                }

            Scaffold(
                topBar = {
                    MaintainerAppBar(
                        modifier = Modifier.height(DIM_XXL),
                        title = currentScreen.title,
                        showBackButton = currentScreen.title != Overview.title,
                        showAddButton = addScreen != null,
                        onBackClick = { navController.navigateUp() },
                        onAddClick = { navigateToCreation() },
                        onSettingsClick = { navController.navigateToSettings() }
                    )
                }
            ) { innerPadding ->
                MaintainerNavGraph(
                    navController = navController,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}
