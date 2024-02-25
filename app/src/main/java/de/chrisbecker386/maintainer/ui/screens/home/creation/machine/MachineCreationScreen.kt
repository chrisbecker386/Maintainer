/*
 * Created by Christopher Becker on 16/12/2023, 15:22
 * Copyright (c) 2023. All rights reserved.
 * Last modified 16/12/2023, 15:22
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

package de.chrisbecker386.maintainer.ui.screens.home.creation.machine

import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.chrisbecker386.maintainer.data.entity.Machine
import de.chrisbecker386.maintainer.data.model.DataResourceState
import de.chrisbecker386.maintainer.ui.component.BaseButton
import de.chrisbecker386.maintainer.ui.component.ImagePickerWithPreview
import de.chrisbecker386.maintainer.ui.component.TextInputField
import de.chrisbecker386.maintainer.ui.component.basic.CircularLoadIndicator
import de.chrisbecker386.maintainer.ui.component.basic.MessageFullScreen
import de.chrisbecker386.maintainer.ui.theme.DIM_S
import de.chrisbecker386.maintainer.ui.theme.DIM_XS
import de.chrisbecker386.maintainer.ui.theme.ICON_LIST

@Composable
fun MachineCreationScreen(
    id: Int? = null,
    foreignId: Int,
    navigateUp: () -> Unit
) {
    val viewModel = hiltViewModel<MachineCreationViewModel>()
    val state by viewModel.machineEditState.collectAsStateWithLifecycle()

    when (state) {
        is DataResourceState.Error ->
            MessageFullScreen(
                title = "Error",
                message = (state as DataResourceState.Error).message,
                onClick = { viewModel.onEvent(MachineEditEvent.AcceptError) }
            )

        is DataResourceState.Loading -> CircularLoadIndicator()

        is DataResourceState.Success -> MachineCreation(
            state = (state as DataResourceState.Success<MachineEditData>).data,
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
private fun MachineCreation(
    state: MachineEditData,
    onEvent: (MachineEditEvent) -> Unit = {}
) {
    var machine: Machine by remember {
        mutableStateOf(
            Machine(
                state.id ?: 0,
                state.title ?: "",
                state.subtitle,
                state.imageRes ?: ICON_LIST.first(),
                state.foreignId
            )
        )
    }
    Log.d("Foreign_Id", state.foreignId.toString())
    val focusManager = LocalFocusManager.current

    LazyColumn(
        Modifier
            .fillMaxWidth()
            .padding(DIM_XS)
    ) {
        item {
            TextInputField(
                label = "Machine Name",
                value = machine.title,
                onValueChange = { machine = machine.copy(title = it) },
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
                value = machine.subtitle ?: "",
                onValueChange = { machine = machine.copy(subtitle = it) },
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
        item {
            ImagePickerWithPreview(
                title = "Select a Icon",
                images = ICON_LIST,
                onImageChange = { machine = machine.copy(imageRes = it) }
            )
        }

        item {
            Spacer(modifier = Modifier.height(DIM_S))
            BaseButton(
                enable = machine.title.isNotEmpty(),
                modifier = Modifier.fillMaxWidth(),
                text = "confirm",
                onClick = { onEvent(MachineEditEvent.UpsertMachine(machine)) }
            )
        }
    }
}
