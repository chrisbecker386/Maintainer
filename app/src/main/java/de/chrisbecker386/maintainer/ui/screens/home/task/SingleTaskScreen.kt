/*
 * Created by Christopher Becker on 23/05/2023, 10:45
 * Copyright (c) 2023. All rights reserved.
 * Last modified 23/05/2023, 10:45
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

package de.chrisbecker386.maintainer.ui.screens.home.task

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import de.chrisbecker386.maintainer.ui.component.ConfirmDialog
import de.chrisbecker386.maintainer.ui.component.ShortStatus
import de.chrisbecker386.maintainer.ui.component.StepWithDetails
import de.chrisbecker386.maintainer.ui.theme.DIM_XS
import de.chrisbecker386.maintainer.ui.theme.MaintainerTheme

@Composable
fun SingleTaskScreen(
    taskType: Int,
    navigateUp: () -> Unit = {}
) {
    val viewModel = hiltViewModel<SingleTaskViewModel>()
    val state by viewModel.state.collectAsState()

    SingleTask(
        state = state,
        onEvent = viewModel::onEvent,
        navigateUp = navigateUp
    )
}

@Composable
private fun SingleTask(
    state: SingleTaskState,
    onEvent: (SingleTaskEvent) -> Unit = {},
    navigateUp: () -> Unit
) {
    if (state.isTaskDone) onEvent(SingleTaskEvent.TaskDone(state.task))
    if (state.showDialog) {
        ConfirmDialog(
            onConfirm = navigateUp,
            title = "Task Completed",
            text = "You have completed this task! \uD83D\uDCAA"
        )
    }

    LazyColumn(
        Modifier
            .fillMaxWidth()
            .padding(start = DIM_XS, end = DIM_XS, top = DIM_XS),
        verticalArrangement = Arrangement.spacedBy(DIM_XS)
    ) {
        item {
            ShortStatus(
                title = state.task.title,
                state = state.shortStatus
            )
        }
        items(count = state.steps.size) { index ->
            StepWithDetails(
                Modifier.clickable { onEvent(SingleTaskEvent.StepDone(state.steps[index])) },
                step = state.steps[index],
                task = state.task
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewSingleTaskScreen() {
    MaintainerTheme {
        SingleTaskScreen(1)
    }
}
