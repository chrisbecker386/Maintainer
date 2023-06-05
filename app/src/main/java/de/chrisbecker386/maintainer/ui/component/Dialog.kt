/*
 * Created by Christopher Becker on 07/06/2023, 09:14
 * Copyright (c) 2023. All rights reserved.
 * Last modified 07/06/2023, 09:14
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
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import de.chrisbecker386.maintainer.ui.theme.DIM_NO
import de.chrisbecker386.maintainer.ui.theme.DIM_S
import de.chrisbecker386.maintainer.ui.theme.DIM_XS
import de.chrisbecker386.maintainer.ui.theme.DIM_XXXXS
import de.chrisbecker386.maintainer.ui.theme.MaintainerTheme

@Composable
fun ConfirmDialog(
    title: String = "title",
    text: String = "text",
    confirmText: String = "confirm",
    onConfirm: () -> Unit = {},
    onDismissRequest: () -> Unit = {}
) {
    var isConfirmClickable by rememberSaveable { mutableStateOf(true) }
    var isOnDismissClickable by rememberSaveable { mutableStateOf(true) }

    Dialog(
        onDismissRequest = {
            if (isOnDismissClickable) {
                isOnDismissClickable = false
                onDismissRequest()
            }
        }
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(1f),
            shape = RoundedCornerShape(DIM_S),
            border = BorderStroke(
                width = DIM_XXXXS,
                color = MaterialTheme.colors.onBackground
            ),
            elevation = DIM_NO
        ) {
            Column(
                modifier = Modifier
                    .padding(start = DIM_XS, end = DIM_XS, top = DIM_XS, bottom = DIM_XS),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.onBackground
                )
                Text(
                    text = text,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onBackground
                )

                Spacer(modifier = Modifier.height(DIM_XS))
                Button(onClick = {
                    if (isConfirmClickable) {
                        isConfirmClickable = false
                        onConfirm()
                    }
                }, shape = RoundedCornerShape(DIM_XS)) {
                    Text(
                        text = confirmText,
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onBackground
                    )
                }
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewConfirmDialog() {
    MaintainerTheme {
        ConfirmDialog()
    }
}
