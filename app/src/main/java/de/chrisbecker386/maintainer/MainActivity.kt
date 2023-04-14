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
import de.chrisbecker386.maintainer.navigation.APP_TABS
import de.chrisbecker386.maintainer.navigation.MaintainerAppBar
import de.chrisbecker386.maintainer.navigation.MaintainerBottomBar
import de.chrisbecker386.maintainer.navigation.MaintainerNavGraph
import de.chrisbecker386.maintainer.navigation.Screen
import de.chrisbecker386.maintainer.navigation.navigateWithPopUp
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
            ) { innerPadding ->
                MaintainerNavGraph(
                    navController = navController,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}
