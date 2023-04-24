/*
 * Created by Christopher Becker on 17/04/2023, 06:48
 * Copyright (c) 2023. All rights reserved.
 * Last modified 17/04/2023, 06:48
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

package de.chrisbecker386.maintainer.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.chrisbecker386.maintainer.data.model.TaskObject
import de.chrisbecker386.maintainer.data.model.dummy.dummyTasks
import de.chrisbecker386.maintainer.ui.theme.DIM_S
import de.chrisbecker386.maintainer.ui.theme.DIM_XS
import de.chrisbecker386.maintainer.ui.theme.DIM_XXXS
import de.chrisbecker386.maintainer.ui.theme.MaintainerTheme

@Composable
fun Task(modifier: Modifier = Modifier, data: TaskObject) {
    Box(modifier = modifier) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = DIM_XXXS,
            shape = RoundedCornerShape(DIM_S),
            backgroundColor = MaterialTheme.colors.primaryVariant
        ) {
            Column {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.primaryVariant)
                        .padding(DIM_XS),

                    text = data.title,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h5
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.background)
                        .padding(DIM_XS)
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Column(Modifier.fillMaxWidth()) {
                            data.steps.forEach { step ->
                                Step(modifier = Modifier.padding(bottom = DIM_XS), data = step)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewTask() {
    MaintainerTheme {
        Column {
            Task(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                data = dummyTasks[0]
            )
            Task(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                data = dummyTasks[1]
            )
        }
    }
}
