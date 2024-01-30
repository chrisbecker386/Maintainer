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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import de.chrisbecker386.maintainer.ui.component.ImagePickerWithPreview
import de.chrisbecker386.maintainer.ui.component.editables.DualTextInput
import de.chrisbecker386.maintainer.ui.component.editables.RepeatFrequencyEditor
import de.chrisbecker386.maintainer.ui.component.editables.StepsEditor
import de.chrisbecker386.maintainer.ui.theme.DIM_XS
import de.chrisbecker386.maintainer.ui.theme.ICON_LIST
import de.chrisbecker386.maintainer.ui.theme.MaintainerTheme

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
                    fields = Pair("", ""),
                    labels = Pair("name", "subtitle"),
                    onValueChange = { first, second ->
                        onEvent(TaskCreationEvent.TitleChange(first ?: ""))
                        onEvent(TaskCreationEvent.SubtitleChange(second ?: ""))
                    }
                )
            }
            // image picker
            // TODO add functionality to the viewmodel with the params
            item { ImagePickerWithPreview(title = "", images = ICON_LIST) }
            // repeat frequency
            // TODO add functionality to the viewmodel with the params
            item { RepeatFrequencyEditor() }
            // steps
            //TODO add functionality to the viewmodel with the params
            item { StepsEditor() }
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
