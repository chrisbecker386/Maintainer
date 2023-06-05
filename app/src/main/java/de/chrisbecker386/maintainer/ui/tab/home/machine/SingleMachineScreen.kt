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

package de.chrisbecker386.maintainer.ui.tab.home.machine

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import de.chrisbecker386.maintainer.data.model.ApproximateTime
import de.chrisbecker386.maintainer.ui.component.ShortStatus
import de.chrisbecker386.maintainer.ui.component.TaskContent
import de.chrisbecker386.maintainer.ui.theme.DIM_XS
import de.chrisbecker386.maintainer.ui.theme.MaintainerTheme

@Composable
fun SingleMachineScreen(
    machineType: Int,
    onTaskClick: (Int) -> Unit = {}
) {
    val viewModel = hiltViewModel<SingleMachineViewModel>()
    val state by viewModel.state.collectAsState()
    SingleMachine(state = state, onTaskClick = onTaskClick)
}

@Composable
private fun SingleMachine(
    state: SingleMachineState,
    onEvent: (SingleMachineEvent) -> Unit = {},
    onTaskClick: (Int) -> Unit = {}
) {
    LazyColumn(Modifier.fillMaxWidth()) {
        item {
            ShortStatus(
                modifier = Modifier.padding(start = DIM_XS, end = DIM_XS, top = DIM_XS),
                title = state.machine.title,
                state = state.shortStatus
            )
        }
        item {
            Text(
                modifier = Modifier.padding(start = DIM_XS, end = DIM_XS, top = DIM_XS),
                text = "Open",
                style = MaterialTheme.typography.h3
            )
        }
        items(count = state.openTasks.size) { index ->
            TaskContent(
                modifier = Modifier
                    .clickable { onTaskClick(state.openTasks[index].task.id) }
                    .padding(start = DIM_XS, end = DIM_XS, top = DIM_XS),
                title = state.openTasks[index].task.title,
                subtitle = state.openTasks[index].task.subtitle ?: "none",
                approximateTime = ApproximateTime.MIN_45,
                preconditions = state.openTasks[index].preconditions.size,
                numberOfSteps = state.openTasks[index].steps.size
            )
        }
        item {
            Text(
                modifier = Modifier.padding(start = DIM_XS, end = DIM_XS, top = DIM_XS),
                text = "Closed",
                style = MaterialTheme.typography.h3
            )
        }
        items(count = state.closedTasks.size) { index ->
            TaskContent(
                modifier = Modifier
                    .clickable { onTaskClick(state.closedTasks[index].task.id) }
                    .padding(start = DIM_XS, end = DIM_XS, top = DIM_XS),
                title = state.closedTasks[index].task.title,
                subtitle = state.closedTasks[index].task.subtitle ?: "none",
                approximateTime = ApproximateTime.MIN_45,
                preconditions = state.closedTasks[index].preconditions.size,
                numberOfSteps = state.closedTasks[index].steps.size
            )
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewSingleMachine() {
    MaintainerTheme {
        SingleMachineScreen(machineType = 1)
    }
}
