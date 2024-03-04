/*
 * Created by Christopher Becker on 16/12/2023, 15:23
 * Copyright (c) 2023. All rights reserved.
 * Last modified 16/12/2023, 15:23
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

package de.chrisbecker386.maintainer.ui.screens.home.creation.task

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.chrisbecker386.maintainer.R
import de.chrisbecker386.maintainer.data.model.DataResourceState
import de.chrisbecker386.maintainer.ui.component.BaseButton
import de.chrisbecker386.maintainer.ui.component.ImagePickerWithPreview
import de.chrisbecker386.maintainer.ui.component.basic.CircularLoadIndicator
import de.chrisbecker386.maintainer.ui.component.basic.MessageFullScreen
import de.chrisbecker386.maintainer.ui.component.editables.DualTextInput
import de.chrisbecker386.maintainer.ui.component.editables.RepeatFrequencyEditor
import de.chrisbecker386.maintainer.ui.component.editables.StepsEditor
import de.chrisbecker386.maintainer.ui.theme.DIM_XS
import de.chrisbecker386.maintainer.ui.theme.ICON_LIST
import de.chrisbecker386.maintainer.ui.theme.MaintainerTheme
import java.time.LocalDateTime
import java.time.ZoneId

@Composable
fun TaskCreationScreen(
    id: Int? = null,
    foreignId: Int,
    navigateUp: () -> Unit = {}
) {
    val viewModel = hiltViewModel<TaskCreationViewModel>()
    val state by viewModel.taskEditState.collectAsStateWithLifecycle()

    when (state) {
        is DataResourceState.Error ->
            MessageFullScreen(
                title = "Error",
                message = (state as DataResourceState.Error).message,
                onClick = { viewModel.onEvent(TaskCreationEvent.AcceptError) }
            )

        is DataResourceState.Loading -> CircularLoadIndicator()

        is DataResourceState.Success -> TaskCreation(
            state = (state as DataResourceState.Success<TaskEditData>).data,
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
private fun TaskCreation(
    state: TaskEditData,
    onEvent: (TaskCreationEvent) -> Unit = {}
) {
    var task by remember { mutableStateOf(state.task) }
    var steps by rememberSaveable { mutableStateOf(state.steps) }
    var startDateTime by rememberSaveable {
        mutableLongStateOf(
            state.startDateTime
                ?: LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond()
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(DIM_XS)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(DIM_XS)
        ) {
            // task declaration
            item {
                DualTextInput(
                    fields = Pair(task.title, task.subtitle),
                    labels = Pair("name", "subtitle"),
                    onValueChange = { first, second ->
                        task = task.copy(title = first ?: "", subtitle = second)
                    }
                )
            }
            // image picker
            item {
                ImagePickerWithPreview(
                    title = "",
                    images = ICON_LIST,
                    imageRes = task.imageRes,
                    onImageChange = { task = task.copy(imageRes = it) }
                )
            }
            // repeat frequency
            item {
                RepeatFrequencyEditor(
                    startDateTime = startDateTime,
                    repeatFrequencyAndTact = Pair(task.repeatFrequency, task.tact.toInt()),
                    onDateTimeChange = { startDateTime = it },
                    onRepeatFrequencyAndTactChange = {
                        task = task.copy(repeatFrequency = it.first, tact = it.second.toLong())
                    }
                )
            }
            // steps
            item {
                StepsEditor(
                    steps = steps,
                    onValueChange = { steps = it }
                )
            }
            // add/update Button
            item {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Absolute.Right) {
                    BaseButton(
                        text = if ((state.id == null) || (state.id == 0)) {
                            ContextCompat.getString(LocalContext.current, R.string.add)
                        } else {
                            ContextCompat.getString(LocalContext.current, R.string.update)
                        },
                        enable = task.title.isNotEmpty() && steps.isNotEmpty(),
                        onClick = {
                            onEvent(
                                TaskCreationEvent.UpsertTask(
                                    task = task,
                                    startDateTime = startDateTime,
                                    steps = steps
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, heightDp = 1500)
@Composable
fun PreviewTaskCreationScreen() {
    MaintainerTheme {
        TaskCreation(TaskEditData())
    }
}
