/*
 * Created by Christopher Becker on 16/12/2023, 15:24
 * Copyright (c) 2023. All rights reserved.
 * Last modified 16/12/2023, 15:24
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

package de.chrisbecker386.maintainer.ui.screens.home.creation.section

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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.chrisbecker386.maintainer.data.entity.Section
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
fun SectionCreationScreen(
    id: Int? = null,
    navigateUp: () -> Unit
) {
    val viewModel = hiltViewModel<SectionCreationViewModel>()
    val state by viewModel.sectionEditState.collectAsStateWithLifecycle()

    when (state) {
        is DataResourceState.Error ->
            MessageFullScreen(
                title = "Error",
                message = (state as DataResourceState.Error).message,
                onClick = { viewModel.onEvent(SectionCreationEvent.AcceptError) }
            )

        is DataResourceState.Loading -> CircularLoadIndicator()

        is DataResourceState.Success -> SectionCreation(
            state = (state as DataResourceState.Success).data,
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
private fun SectionCreation(
    state: SectionCreationData,
    onEvent: (SectionCreationEvent) -> Unit = {}
) {
    var section: Section by remember {
        mutableStateOf(
            Section(
                state.id ?: 0,
                state.title ?: "",
                state.imageRes ?: ICON_LIST.first()
            )
        )
    }

    val focusManager = LocalFocusManager.current

    LazyColumn(Modifier.fillMaxWidth().padding(DIM_XS)) {
        item {
            TextInputField(
                label = "Section Name",
                value = section.title,
                onValueChange = { section = section.copy(title = it) },
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
            ImagePickerWithPreview(
                title = "Select a Icon",
                images = ICON_LIST,
                onImageChange = { section = section.copy(imageRes = it) }
            )
            Spacer(modifier = Modifier.height(DIM_S))
            BaseButton(
                enable = section.title.isNotEmpty(),
                text = "confirm",
                onClick = { onEvent(SectionCreationEvent.UpsertSection(section)) }
            )
        }
    }
}
