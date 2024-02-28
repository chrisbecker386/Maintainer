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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.chrisbecker386.maintainer.data.model.DataResourceState
import de.chrisbecker386.maintainer.ui.component.ShortStatus
import de.chrisbecker386.maintainer.ui.component.StepWithDetails
import de.chrisbecker386.maintainer.ui.component.basic.CircularLoadIndicator
import de.chrisbecker386.maintainer.ui.component.basic.MessageFullScreen
import de.chrisbecker386.maintainer.ui.theme.DIM_XS
import de.chrisbecker386.maintainer.ui.theme.MaintainerTheme

@Composable
fun SingleTaskScreen(
    taskType: Int,
    navigateUp: () -> Unit = {}
) {
    val viewModel = hiltViewModel<SingleTaskViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    when (state) {
        is DataResourceState.Error ->
            MessageFullScreen(
                title = "Error",
                message = (state as DataResourceState.Error).message,
                onClick = { viewModel.onEvent(SingleTaskEvent.AcceptError) }
            )

        is DataResourceState.Loading -> CircularLoadIndicator()

        is DataResourceState.Success -> SingleTask(
            state = (state as DataResourceState.Success<SingleTaskData>).data,
            onEvent = viewModel::onEvent
        )

        is DataResourceState.Finished -> MessageFullScreen(
            title = (state as DataResourceState.Finished).title ?: "success title",
            message = (state as DataResourceState.Finished).text ?: "success text",
            onClick = { navigateUp() }
        )
    }
}

@Composable
private fun SingleTask(
    state: SingleTaskData,
    onEvent: (SingleTaskEvent) -> Unit = {}
) {
    if ((state.shortStatus.denominator > 0) && (state.shortStatus.denominator == state.shortStatus.numerator)) {
        onEvent(SingleTaskEvent.UpsertTask(state.task))
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
                Modifier.clickable { onEvent(SingleTaskEvent.UpsertStep(state.steps[index])) },
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
