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

package de.chrisbecker386.maintainer.ui.screens.home.overview

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import de.chrisbecker386.maintainer.data.model.GridItemData
import de.chrisbecker386.maintainer.ui.component.NextMaintains
import de.chrisbecker386.maintainer.ui.component.OverviewGrid
import de.chrisbecker386.maintainer.ui.component.ShortStatus
import de.chrisbecker386.maintainer.ui.theme.DIM_XS

@Composable
fun OverviewScreen(
    modifier: Modifier = Modifier,
    onSectionClick: (Int) -> Unit = {},
    onMachineClick: (Int) -> Unit = {},
    onSectionCreationClick: (Int?) -> Unit = {}
) {
    val viewModel = hiltViewModel<OverviewScreenViewModel>()
    val state by viewModel.state.collectAsState()

    Overview(
        state = state,
        onSectionClick = onSectionClick,
        onMachineClick = onMachineClick,
        onSectionCreationClick = onSectionCreationClick
    )
}

@Composable
private fun Overview(
    state: OverviewState,
    onEvent: (OverviewEvent) -> Unit = {},
    onSectionClick: (Int) -> Unit = {},
    onMachineClick: (Int) -> Unit = {},
    onSectionCreationClick: (Int?) -> Unit = {}
) {
    LazyColumn(Modifier.fillMaxWidth()) {
        item {
            ShortStatus(
                Modifier.padding(start = DIM_XS, end = DIM_XS, top = DIM_XS),
                title = "Maintain Status",
                state = state.shortStatus
            )
        }
        item {
            NextMaintains(
                Modifier
                    .padding(start = DIM_XS, end = DIM_XS, top = DIM_XS)
                    .clickable {
                        state.nextMachine?.id
                            ?.let { onMachineClick(it) }
                    },
                machineTitle = state.nextMachine.let { it?.title } ?: "",
                tasks = state.nextTasks ?: emptyList()
            )
        }
        item {
            val modifiedList = mutableListOf<GridItemData>()
            state.sections.map { it.toGridItemData() }.forEach { modifiedList.add(it) }
            modifiedList.add(GridItemData(0, "section", null))

            val finalList = modifiedList.toList()
            OverviewGrid(
                modifier = Modifier.padding(
                    start = DIM_XS,
                    end = DIM_XS,
                    top = DIM_XS
                ),
                items = finalList,
                onItemClick = { it ->
                    when (it) {
                        0 -> onSectionCreationClick(null)
                        else -> onSectionClick(it)
                    }
                }
            )
        }
    }
}
