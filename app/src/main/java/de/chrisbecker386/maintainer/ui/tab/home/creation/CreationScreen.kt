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

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import de.chrisbecker386.maintainer.R
import de.chrisbecker386.maintainer.data.entity.Section
import de.chrisbecker386.maintainer.navigation.CreationType

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

    LazyColumn(Modifier.fillMaxWidth()) {
        item {
            Text(text = "${state.type?.name}")

            OutlinedTextField(
                value = state.title ?: "",
                onValueChange = { inputText ->
                    onEvent(CreationEvent.TitleChange(inputText))
                }
            )
            Text(text = "${state.title}")

            Button(
                enabled = state.isCreationDone,
                onClick = {
                    onEvent(
                        CreationEvent.SectionDone(
                            Section(
                                title = state.title?.trim() ?: "",
                                imageRes = R.drawable.question_mark_48px
                            )
                        )
                    )
                }
            ) { Text(text = "Confirm") }
        }
    }
}
