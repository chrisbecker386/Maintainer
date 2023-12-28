/*
 * Created by Christopher Becker on 28/12/2023, 09:56
 * Copyright (c) 2023. All rights reserved.
 * Last modified 28/12/2023, 09:56
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

package de.chrisbecker386.maintainer.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import de.chrisbecker386.maintainer.ui.theme.DIM_S
import de.chrisbecker386.maintainer.ui.theme.MaintainerTheme

@Composable
fun PreconditionEditor(
    modifier: Modifier = Modifier,
    state: PreconditionEditorState,
    onEvent: (PreconditionEditorEvent) -> Unit = {}
) {
    Card(modifier = modifier) {
        Column(
            modifier = modifier.padding(DIM_S)
        ) {
            Text(text = "Precondition", style = typography.h2, color = colors.onBackground)
            TextInputField(
                modifier = Modifier.fillMaxWidth(),
                label = "name a precondition",
                value = state.text ?: "",
                onValueChange = { onEvent(PreconditionEditorEvent.ChangeText(it)) },
                keyboardOptions = KeyboardOptions.Default,
                keyboardActions = KeyboardActions.Default
            )
        }
    }
}

data class PreconditionEditorState(
    val text: String? = null
)

interface PreconditionEditorEvent {
    data class ChangeText(val text: String) : PreconditionEditorEvent
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun PreviewPreconditionEditor() {
    var state by remember { mutableStateOf(PreconditionEditorState()) }

    fun onEvent(event: PreconditionEditorEvent) {
        when (event) {
            is PreconditionEditorEvent.ChangeText -> { state = state.copy(text = event.text) }
        }
    }

    MaintainerTheme {
        PreconditionEditor(
            modifier = Modifier.background(color = colors.background),
            state = PreconditionEditorState(),
            onEvent = ::onEvent
        )
    }
}
