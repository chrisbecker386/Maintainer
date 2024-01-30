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

package de.chrisbecker386.maintainer.ui.tab.home.creation.task

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import de.chrisbecker386.maintainer.data.model.RepeatFrequency
import de.chrisbecker386.maintainer.ui.component.ImagePickerWithPreview
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
    val state by viewModel.state.collectAsState()
    TaskCreation(state = state, onEvent = viewModel::onEvent, navigateUp = navigateUp)
}

@Composable
private fun TaskCreation(
    state: TaskCreationState,
    onEvent: (TaskCreationEvent) -> Unit = {},
    navigateUp: () -> Unit = {}
) {
    if (state.isNavigateUp) navigateUp()

    // TODO if all requirements are fulfilled enable create/update Button
    // TODO an update add Button

    // TODO [x] place all data from vm here
    // TODO [x] save all data local in this composable
    var titles by rememberSaveable { mutableStateOf(state.titles) }
    var imageRes by rememberSaveable { mutableStateOf(state.imageRes) }

    var repeatFrequencyAndTact by rememberSaveable {
        mutableStateOf(
            Pair(
                state.repeatFrequencyAndTact.first ?: RepeatFrequency.WEEKLY.value,
                1
            )
        )
    }

    var startDateTime by rememberSaveable {
        mutableLongStateOf(
            state.startDateTime ?: LocalDateTime.now().atZone(
                ZoneId.systemDefault()
            ).toEpochSecond()
        )
    }
    var steps by rememberSaveable { mutableStateOf(state.steps) }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(DIM_XS)
    ) {
        Text(text = "Create Task", style = MaterialTheme.typography.h2)

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(DIM_XS)
        ) {
            // task declaration
            item {
                DualTextInput(
                    fields = titles,
                    labels = Pair("name", "subtitle"),
                    onValueChange = { first, second ->
                        titles = titles.copy(first, second)
                    }
                )
            }
            // image picker
            item {
                ImagePickerWithPreview(
                    title = "",
                    images = ICON_LIST,
                    imageRes = imageRes ?: ICON_LIST.first(),
                    onImageChange = { imageRes = it }
                )
            }
            // repeat frequency
            item {
                RepeatFrequencyEditor(
                    startDateTime = startDateTime,
                    repeatFrequencyAndTact = repeatFrequencyAndTact,
                    onDateTimeChange = { startDateTime = it },
                    onRepeatFrequencyAndTactChange = { repeatFrequencyAndTact = it }
                )
            }
            // steps
            item {
                StepsEditor(
                    steps = steps,
                    onValueChange = { steps = it }
                )
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, heightDp = 1500)
@Composable
fun PreviewTaskCreationScreen() {
    MaintainerTheme {
        TaskCreation(TaskCreationState())
    }
}
