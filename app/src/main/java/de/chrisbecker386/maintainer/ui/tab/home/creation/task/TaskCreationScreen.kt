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

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import de.chrisbecker386.maintainer.ui.component.TextInputField
import de.chrisbecker386.maintainer.ui.theme.DIM_XS

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
    val focusManager = LocalFocusManager.current
    LazyColumn(
        Modifier
            .fillMaxWidth()
            .padding(DIM_XS)
    ) {
        item {
            Text(text = "Create Task", style = MaterialTheme.typography.h2)
        }
        item {
            TextInputField(
                label = "Machine Name",
                value = "",
                onValueChange = { onEvent(TaskCreationEvent.TitleChange(title = it)) },
                enabled = true,
                sideIcon = null,
                onSideIconClick = {},
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                )
            )
        }
        item {
            TextInputField(
                label = "Subtitle",
                value = "",
                onValueChange = { onEvent(TaskCreationEvent.SubtitleChange(subtitle = it)) },
                enabled = true,
                sideIcon = null,
                onSideIconClick = {},
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
            )
        }
    }
}
