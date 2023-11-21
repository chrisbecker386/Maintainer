/*
 * Created by Christopher Becker on 10/05/2023, 12:59
 * Copyright (c) 2023. All rights reserved.
 * Last modified 10/05/2023, 12:59
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

package de.chrisbecker386.maintainer.ui.tab.home.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import de.chrisbecker386.maintainer.data.model.dummy.devMachines
import de.chrisbecker386.maintainer.ui.component.NextMaintains
import de.chrisbecker386.maintainer.ui.component.OverviewGrid
import de.chrisbecker386.maintainer.ui.component.ShortStatus
import de.chrisbecker386.maintainer.ui.model.ShortStatusState
import de.chrisbecker386.maintainer.ui.theme.DIM_XS

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onSectionClick: (Int) -> Unit = {},
    onMachineClick: (Int) -> Unit = {}
) {
    val viewModel = hiltViewModel<HomeScreenViewModel>()
    val state by viewModel.state.collectAsState()
    HomeLanding(state = state, onSectionClick = onSectionClick, onMachineClick = onMachineClick)
}

@Composable
private fun HomeLanding(
    state: HomeLandingState,
    onEvent: (HomeLandingEvent) -> Unit = {},
    onSectionClick: (Int) -> Unit = {},
    onMachineClick: (Int) -> Unit = {}
) {
    LazyColumn(Modifier.fillMaxWidth()) {
        item {
            ShortStatus(
                Modifier.padding(start = DIM_XS, end = DIM_XS, top = DIM_XS),
                title = "Maintain Status",
                state = ShortStatusState(
                    numerator = 6,
                    denominator = 11
                )
            )
        }
        item {
            NextMaintains(
                Modifier
                    .padding(start = DIM_XS, end = DIM_XS, top = DIM_XS)
                    .clickable { onMachineClick(devMachines[0].id) },
                machineTitle = state.nextMachine.let { it?.title } ?: "",
                tasks = state.nextTasks
            )
        }
        item {
            OverviewGrid(
                modifier = Modifier.padding(
                    start = DIM_XS,
                    end = DIM_XS,
                    top = DIM_XS
                ),
                items = state.sections.map { it.toGridItemData() },
                onItemClick = { onSectionClick(it) }
            )
        }
    }
}
