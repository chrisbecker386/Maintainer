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

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.SpaceBetween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import de.chrisbecker386.maintainer.data.entity.Step
import de.chrisbecker386.maintainer.data.model.RepeatFrequency
import de.chrisbecker386.maintainer.data.utility.toRepeatFrequency
import de.chrisbecker386.maintainer.ui.component.AddStepDialog
import de.chrisbecker386.maintainer.ui.component.ImagePickerWithPreview
import de.chrisbecker386.maintainer.ui.component.IntervalPicker
import de.chrisbecker386.maintainer.ui.component.MaintainerDatePickerDialog
import de.chrisbecker386.maintainer.ui.component.MaintainerTimePickerDialog
import de.chrisbecker386.maintainer.ui.component.StepWithDetails
import de.chrisbecker386.maintainer.ui.component.TextInputField
import de.chrisbecker386.maintainer.ui.component.basic.NumberPickerVertical
import de.chrisbecker386.maintainer.ui.theme.BUTTON_CORNER_SHAPE
import de.chrisbecker386.maintainer.ui.theme.DEFAULT_STEP
import de.chrisbecker386.maintainer.ui.theme.DIM_NO
import de.chrisbecker386.maintainer.ui.theme.DIM_S
import de.chrisbecker386.maintainer.ui.theme.DIM_S_PLUS
import de.chrisbecker386.maintainer.ui.theme.DIM_XS
import de.chrisbecker386.maintainer.ui.theme.DIM_XXXXS
import de.chrisbecker386.maintainer.ui.theme.ICON_LIST
import de.chrisbecker386.maintainer.ui.theme.MaintainerTheme
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

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

    var repeatFrequency by remember { mutableStateOf(RepeatFrequency.WEEKLY) }
    var repeatFrequencyCount by remember { mutableIntStateOf(1) }
    var timeState: LocalTime by remember { mutableStateOf(LocalTime.now()) }
    var dateState: LocalDate by remember { mutableStateOf(LocalDate.now()) }
    var currentStep: Step by remember { mutableStateOf(DEFAULT_STEP) }
    val steps by rememberSaveable { mutableStateOf(mutableListOf<Step>()) }

    var showTimePicker by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    var showTaskDialog by remember { mutableStateOf(false) }

    fun getFormattedTime(localTime: LocalTime): String {
        val timeFormat = DateTimeFormatter.ofPattern("HH:mm")
        return localTime.format(timeFormat)
    }

    fun getFormattedDate(localDate: LocalDate): String {
        val dateFormat = DateTimeFormatter.ofPattern("EEE, dd. MMM")
        return localDate.format(dateFormat)
    }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(DIM_XS)
    ) {
        Text(text = "Create Task", style = MaterialTheme.typography.h2)

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(DIM_XS)
        ) {
            //task declaration
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(
                        topStart = DIM_S,
                        topEnd = DIM_S,
                        bottomStart = DIM_S_PLUS,
                        bottomEnd = DIM_S_PLUS
                    ),
                    elevation = DIM_NO,
                    border = BorderStroke(
                        width = DIM_XXXXS,
                        color = MaterialTheme.colors.onBackground
                    )
                ) {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = DIM_XS, bottom = DIM_S, start = DIM_XS, end = DIM_XS)
                    ) {
                        TextInputField(
                            label = "name",
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
            //image picker
            item { ImagePickerWithPreview(title = "", images = ICON_LIST) }
            //repeat frequency
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(
                        topStart = DIM_S,
                        topEnd = DIM_S,
                        bottomStart = DIM_S_PLUS,
                        bottomEnd = DIM_S_PLUS
                    ),
                    elevation = DIM_NO,
                    border = BorderStroke(
                        width = DIM_XXXXS,
                        color = MaterialTheme.colors.onBackground
                    )
                ) {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = DIM_XS, bottom = DIM_S, start = DIM_XS, end = DIM_XS),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "repeat frequency",
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.onBackground
                        )
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Starts on",
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.onBackground
                        )

                        // Start date
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = getFormattedDate(dateState),
                                style = MaterialTheme.typography.h2,
                                color = MaterialTheme.colors.onBackground
                            )
                            Button(
                                onClick = { showDatePicker = !showDatePicker },
                                shape = RoundedCornerShape(BUTTON_CORNER_SHAPE)
                            ) {
                                Text(
                                    text = "set",
                                    style = MaterialTheme.typography.body1,
                                    color = MaterialTheme.colors.onBackground
                                )
                            }
                        }

                        // interval
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "every",
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.onBackground
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row {
                                Text(
                                    text = if (repeatFrequencyCount > 1) "$repeatFrequencyCount." else "",
                                    style = MaterialTheme.typography.h2,
                                    color = MaterialTheme.colors.onBackground
                                )
                                Text(
                                    text = repeatFrequency.text,
                                    style = MaterialTheme.typography.h2,
                                    color = MaterialTheme.colors.onBackground
                                )
                            }
                            NumberPickerVertical(
                                value = 1,
                                onValueChange = { repeatFrequencyCount += it }
                            )
                        }
                        IntervalPicker(
                            modifier = Modifier.fillMaxWidth(),
                            interval = state.repeatFrequency?.toRepeatFrequency()
                                ?: RepeatFrequency.WEEKLY,
                            onEvent = {
                                repeatFrequency = it
                                repeatFrequencyCount = 1
                            }
                        )
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "at",
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.onBackground
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = getFormattedTime(timeState),
                                style = MaterialTheme.typography.h2,
                                color = MaterialTheme.colors.onBackground
                            )
                            Button(
                                onClick = { showTimePicker = !showTimePicker },
                                shape = RoundedCornerShape(25)
                            ) {
                                Text(
                                    text = "set",
                                    style = MaterialTheme.typography.body1,
                                    color = MaterialTheme.colors.onBackground
                                )
                            }
                        }
                        if (showDatePicker) {
                            MaintainerDatePickerDialog(
                                localDate = dateState,
                                onConfirm = {
                                    dateState = it
                                    showDatePicker = false
                                },
                                onDismissRequest = { showDatePicker = false }
                            )
                        }
                        if (showTimePicker) {
                            MaintainerTimePickerDialog(
                                localTime = timeState,
                                onConfirm = {
                                    timeState = it
                                    showTimePicker = false
                                },
                                onDismissRequest = { showTimePicker = false }
                            )
                        }
                    }
                }
            }
            // steps
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(
                        topStart = DIM_S,
                        topEnd = DIM_S,
                        bottomStart = DIM_S_PLUS,
                        bottomEnd = DIM_S_PLUS
                    ),
                    elevation = DIM_NO,
                    border = BorderStroke(
                        width = DIM_XXXXS,
                        color = MaterialTheme.colors.onBackground
                    )
                ) {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = DIM_XS, bottom = DIM_S, start = DIM_XS, end = DIM_XS),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "steps",
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.onBackground
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Button(
                                onClick = { showTaskDialog = !showTaskDialog },
                                shape = RoundedCornerShape(BUTTON_CORNER_SHAPE)
                            ) {
                                Text(
                                    text = "add",
                                    style = MaterialTheme.typography.body1,
                                    color = MaterialTheme.colors.onBackground
                                )
                            }
                        }

                        if (steps.size > 0) {
                            steps.forEachIndexed { index, step ->
                                StepWithDetails(
                                    modifier = Modifier.pointerInput(Unit) {
                                        detectTapGestures(
                                            onLongPress = {
                                                currentStep = steps[index]
                                                showTaskDialog = !showTaskDialog
                                            }
                                        )
                                    },
                                    step = step
                                )
                            }
                        }
                        if (showTaskDialog) {
                            AddStepDialog(
                                step = currentStep,
                                onConfirm = { step ->
                                    if (step.order == 0) {
                                        steps.add(
                                            Step(
                                                order = steps.size + 1,
                                                id = 0,
                                                title = step.title,
                                                imageRes = step.imageRes,
                                                description = step.description,
                                                completedDate = step.completedDate,
                                                taskId = step.taskId
                                            )
                                        )
                                    } else {
                                        steps[step.order - 1] = step
                                    }
                                    currentStep = DEFAULT_STEP
                                    showTaskDialog = false
                                },
                                onDismissRequest = { showTaskDialog = false }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, heightDp = 1500)
@Composable
fun PreviewTaskCreationScreen() {
    MaintainerTheme {
        TaskCreation(TaskCreationState())
    }
}
