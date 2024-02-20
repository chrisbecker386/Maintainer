/*
 * Created by Christopher Becker on 30/01/2024, 15:59
 * Copyright (c) 2024. All rights reserved.
 * Last modified 30/01/2024, 15:59
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

package de.chrisbecker386.maintainer.ui.component.editables

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import de.chrisbecker386.maintainer.ui.component.TextInputField
import de.chrisbecker386.maintainer.ui.component.UnevenCard
import de.chrisbecker386.maintainer.ui.theme.DIM_S
import de.chrisbecker386.maintainer.ui.theme.DIM_XS
import de.chrisbecker386.maintainer.ui.theme.MaintainerTheme

/**
 * Composable for a dual text input with two fields.
 *
 * @param modifier Modifier for styling and layout adjustments.
 * @param fields Pair of current values for the two input fields.
 * @param labels Pair of labels for the two input fields.
 * @param onValueChange Lambda invoked when input field values change.
 * @param content Optional composable lambda for additional content.
 */
@Composable
fun DualTextInput(
    modifier: Modifier = Modifier,
    fields: Pair<String?, String?> = Pair(null, null),
    labels: Pair<String?, String?> = Pair(null, null),
    onValueChange: (String?, String?) -> Unit = { _, _ -> },
    content: @Composable () -> Unit = {}
) {
    var localField1 by rememberSaveable { mutableStateOf(fields.first) }
    var localField2 by rememberSaveable { mutableStateOf(fields.second) }

    val focusManager = LocalFocusManager.current

    UnevenCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = DIM_XS, bottom = DIM_S, start = DIM_XS, end = DIM_XS)
    ) {
        TextInputField(
            label = labels.first ?: "",
            value = localField1 ?: "",
            onValueChange = {
                localField1 = it
                onValueChange(it, localField2)
            },
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
        TextInputField(
            label = labels.second ?: "",
            value = localField2 ?: "",
            onValueChange = {
                localField2 = it
                onValueChange(localField1, it)
            },
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
        content()
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewDualTextInput() {
    MaintainerTheme {
        DualTextInput(
            labels = Pair("first", "second"),
            fields = Pair("some text", null)
        )
    }
}
