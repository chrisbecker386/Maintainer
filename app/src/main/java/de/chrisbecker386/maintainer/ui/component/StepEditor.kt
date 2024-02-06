/*
 * Created by Christopher Becker on 19/12/2023, 16:41
 * Copyright (c) 2023. All rights reserved.
 * Last modified 19/12/2023, 16:41
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

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import de.chrisbecker386.maintainer.R
import de.chrisbecker386.maintainer.data.entity.Step
import de.chrisbecker386.maintainer.ui.theme.DIM_L_PLUS
import de.chrisbecker386.maintainer.ui.theme.DIM_M
import de.chrisbecker386.maintainer.ui.theme.DIM_M_PLUS
import de.chrisbecker386.maintainer.ui.theme.DIM_NO
import de.chrisbecker386.maintainer.ui.theme.DIM_S
import de.chrisbecker386.maintainer.ui.theme.DIM_XS
import de.chrisbecker386.maintainer.ui.theme.DIM_XXXS
import de.chrisbecker386.maintainer.ui.theme.DIM_XXXXS
import de.chrisbecker386.maintainer.ui.theme.MaintainerTheme

@Composable
fun StepEditor(
    modifier: Modifier = Modifier,
    state: Step,
    onEvent: (StepCreationEvent) -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(
            topStart = DIM_M,
            topEnd = DIM_M,
            bottomEnd = DIM_M_PLUS,
            bottomStart = DIM_M_PLUS
        ),
        border = BorderStroke(DIM_XXXXS, colors.onBackground),
        backgroundColor = colors.background,
        elevation = DIM_NO
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(DIM_S)
        ) {
            Text("new Step", color = colors.onBackground)
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = DIM_XS, bottom = DIM_XS),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(DIM_L_PLUS)
                        .background(
                            color = MaterialTheme.colors.primaryVariant,
                            shape = RoundedCornerShape(DIM_M)
                        ),
                    Alignment.Center
                ) {
                    Text(
                        text = state.order.toString(),
                        style = typography.h6,
                        color = colors.background
                    )
                }

                Spacer(Modifier.size(DIM_S))
                Column {
                    SingleIconButton(
                        onClick = { onEvent(StepCreationEvent.OrderChange()) },
                        imageRes = R.drawable.expand_less_48px,
                        colors = ButtonColorsImpl(
                            backgroundColor = colors.background,
                            disabledBackgroundColor = colors.onError
                        )
                    )
                    Spacer(Modifier.size(DIM_XS))
                    SingleIconButton(
                        onClick = { onEvent(StepCreationEvent.OrderChange(1)) },
                        imageRes = R.drawable.expand_more_48px,
                        colors = ButtonColorsImpl(
                            backgroundColor = colors.background,
                            disabledBackgroundColor = colors.onError
                        )
                    )
                }
                Spacer(Modifier.size(DIM_M))
                TextInputField(
                    modifier = Modifier.padding(top = DIM_XXXS, end = DIM_XXXS),
                    label = "Title",
                    onValueChange = { onEvent(StepCreationEvent.TitleChange(it)) }
                )
            }
            MultilineTextInputField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(DIM_XXXS),
                label = "description",
                maxChars = null,
                text = "",
                onValueChange = { onEvent(StepCreationEvent.DescriptionChange(it)) }
            )
        }
    }
}

interface StepCreationEvent {
    data class TitleChange(val title: String) : StepCreationEvent
    data class DescriptionChange(val description: String) : StepCreationEvent
    data class OrderChange(val position: Int = -1) : StepCreationEvent
}

@Preview(showBackground = true, backgroundColor = 0, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewStepEditor() {
    MaintainerTheme {
        var stepState by remember {
            mutableStateOf(
                Step(
                    id = 0,
                    order = 0,
                    title = "",
                    description = null,
                    completedDate = null,
                    taskId = 0
                )
            )
        }

        fun onEvent(event: StepCreationEvent) {
            when (event) {
                is StepCreationEvent.OrderChange -> {
                    stepState = stepState.copy(order = stepState.order + event.position)
                }

                is StepCreationEvent.TitleChange -> {
                    stepState = stepState.copy(title = event.title)
                }

                is StepCreationEvent.DescriptionChange -> {
                    stepState = stepState.copy(description = event.description)
                }
            }
        }
        StepEditor(
            state = stepState,
            onEvent = ::onEvent
        )
    }
}
