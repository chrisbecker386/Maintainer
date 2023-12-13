/*
 * Created by Christopher Becker on 01/12/2023, 13:43
 * Copyright (c) 2023. All rights reserved.
 * Last modified 01/12/2023, 13:43
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

package de.chrisbecker386.maintainer.ui.tab.home.creation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import de.chrisbecker386.maintainer.data.entity.Section
import de.chrisbecker386.maintainer.navigation.CreationType
import de.chrisbecker386.maintainer.ui.component.ImagePickerWithPreview
import de.chrisbecker386.maintainer.ui.component.RoundedButton
import de.chrisbecker386.maintainer.ui.component.TextInputField
import de.chrisbecker386.maintainer.ui.theme.DIM_S
import de.chrisbecker386.maintainer.ui.theme.DIM_XS
import de.chrisbecker386.maintainer.ui.theme.ICON_LIST

@Composable
fun CreationScreen(
    id: Int? = null,
    creationType: CreationType,
    navigateUp: () -> Unit
) {
    val viewModel = hiltViewModel<CreationScreenViewModel>()
    val state by viewModel.state.collectAsState()
    Creation(state = state, onEvent = viewModel::onEvent, navigateUp = navigateUp)
}

@Composable
private fun Creation(
    state: CreationState,
    onEvent: (CreationEvent) -> Unit = {},
    navigateUp: () -> Unit
) {
    if (state.isNavigateUp) navigateUp()
    val focusManager = LocalFocusManager.current
    LazyColumn(
        Modifier
            .fillMaxWidth()
            .padding(DIM_XS)
    ) {
        item { Text(text = "Create ${state.type?.name}", style = MaterialTheme.typography.h2) }
        item {
            TextInputField(
                label = "Section Name",
                value = "",
                onValueChange = { onEvent(CreationEvent.TitleChange(title = it)) },
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
                onImageChange = { onEvent(CreationEvent.ImageChange(imageRes = it)) }
            )

            if ((state.title?.trim() != null) && (state.imageRes != null)) {
                Spacer(modifier = Modifier.height(DIM_S))
                RoundedButton(
                    modifier = Modifier.fillMaxWidth(),
                    title = "confirm",
                    onClick = {
                        onEvent(
                            CreationEvent.SectionConfirm(
                                Section(
                                    title = state.title,
                                    imageRes = state.imageRes
                                )
                            )
                        )
                    }
                )
            }
        }
    }
}
