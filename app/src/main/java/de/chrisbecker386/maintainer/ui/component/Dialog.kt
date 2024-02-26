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

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import de.chrisbecker386.maintainer.data.entity.Step
import de.chrisbecker386.maintainer.ui.screens.settings.MaintainerPermission
import de.chrisbecker386.maintainer.ui.theme.DEFAULT_STEP
import de.chrisbecker386.maintainer.ui.theme.DIM_NO
import de.chrisbecker386.maintainer.ui.theme.DIM_S
import de.chrisbecker386.maintainer.ui.theme.DIM_XS
import de.chrisbecker386.maintainer.ui.theme.DIM_XXS
import de.chrisbecker386.maintainer.ui.theme.MaintainerTheme
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId.systemDefault
import java.time.ZoneOffset

@Composable
fun ConfirmDialog(
    title: String = "title",
    text: String = "text",
    confirmText: String = "confirm",
    onConfirm: () -> Unit = {},
    onDismissRequest: () -> Unit = {}
) {
    var isConfirmClickable by remember { mutableStateOf(true) }
    var isOnDismissClickable by remember { mutableStateOf(true) }

    Dialog(
        onDismissRequest = {
            if (isOnDismissClickable) {
                isOnDismissClickable = false
                onDismissRequest()
            }
        }
    ) {
        EvenCard(horizontalAlignment = Alignment.CenterHorizontally) {
            HeadlineBoldMedium(title)
            BodyText(text = text)
            Spacer(modifier = Modifier.height(DIM_XS))
            Row(horizontalArrangement = Arrangement.End) {
                BaseButton(text = confirmText, onClick = {
                    if (isConfirmClickable) {
                        isConfirmClickable = false
                        onConfirm()
                    }
                })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaintainerTimePickerDialog(
    title: String = "Choose time",
    text: String = "At what time do you want to be reminded?",
    confirmText: String = "Ok",
    localTime: LocalTime = LocalTime.now(),
    is24Hours: Boolean = true,
    onConfirm: (LocalTime) -> Unit = {},
    onDismissRequest: (LocalTime) -> Unit = {}
) {
    val timeState = rememberTimePickerState(
        initialHour = localTime.hour,
        initialMinute = localTime.minute,
        is24Hour = is24Hours
    )

    fun getLocalTime() = LocalTime.of(timeState.hour, timeState.minute)

    Dialog(onDismissRequest = { onDismissRequest(getLocalTime()) }) {
        Card(
            modifier = Modifier.fillMaxWidth(1f),
            shape = RoundedCornerShape(12),
            backgroundColor = colors.background,
            elevation = DIM_NO
        ) {
            Column(
                modifier = Modifier.padding(
                    start = DIM_XS,
                    end = DIM_XS,
                    top = DIM_XS,
                    bottom = DIM_XS
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HeadlineBoldMedium(text = title)
                BodyText(text)
                Spacer(modifier = Modifier.size(DIM_S))
                TimePicker(
                    state = timeState,
                    colors = TimePickerDefaults.colors(
                        clockDialColor = colors.primary,
                        selectorColor = colors.secondary,
                        containerColor = Color.Green,
                        periodSelectorBorderColor = Color.Blue,
                        clockDialSelectedContentColor = colors.onBackground,
                        clockDialUnselectedContentColor = colors.background,
                        timeSelectorSelectedContainerColor = colors.secondary,
                        timeSelectorUnselectedContainerColor = colors.primary,
                        timeSelectorSelectedContentColor = colors.onBackground,
                        timeSelectorUnselectedContentColor = colors.onBackground
                    )
                )
                Spacer(modifier = Modifier.height(DIM_XXS))
                BaseButton(text = confirmText, onClick = {
                    onConfirm(getLocalTime())
                })
                Spacer(modifier = Modifier.height(DIM_XS))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaintainerDatePickerDialog(
    title: String = "Choose date",
    text: String = "When would you like to reminded?",
    confirmText: String = "Ok",
    localDate: LocalDate = LocalDate.now(),
    onConfirm: (LocalDate) -> Unit = {},
    onDismissRequest: (LocalDate) -> Unit = {}
) {
    val dateState = rememberDatePickerState(
        initialSelectedDateMillis = localDate.atStartOfDay(ZoneOffset.MIN)
            .toInstant()
            .toEpochMilli(),
        yearRange = IntRange(localDate.year, LocalDate.now().year + 8),
        initialDisplayMode = DisplayMode.Picker
    )

    fun getLocalDate(): LocalDate = Instant.ofEpochMilli(dateState.selectedDateMillis!!)
        .atZone(systemDefault()).toLocalDate()

    DatePickerDialog(
        onDismissRequest = { onDismissRequest(localDate) },
        confirmButton = {},
        dismissButton = {}
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DatePicker(
                showModeToggle = false,
                state = dateState,
                colors = DatePickerDefaults.colors(
                    containerColor = colors.background,
                    titleContentColor = colors.onBackground,
                    headlineContentColor = colors.onBackground,
                    weekdayContentColor = colors.primary,
                    subheadContentColor = colors.onBackground,
                    yearContentColor = colors.onBackground,
                    currentYearContentColor = colors.primary, // colors.onBackground,
                    selectedYearContentColor = colors.onBackground, // colors.secondary,
                    selectedDayContentColor = colors.onBackground,
                    selectedDayContainerColor = colors.secondary,
                    todayContentColor = colors.onBackground,
                    todayDateBorderColor = colors.primary,
                    selectedYearContainerColor = colors.secondary,
                    dayContentColor = colors.onBackground,
                    disabledDayContentColor = colors.primary,
                    disabledSelectedDayContentColor = colors.onBackground
                )
            )
            BaseButton(text = confirmText, onClick = { onConfirm(getLocalDate()) })
        }
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun AddStepDialog(
    title: String = "Create Task",
    confirmText: String = "Ok",
    step: Step,
    onConfirm: (Step) -> Unit = { },
    onDismissRequest: () -> Unit = {}
) {
    var currentStep by remember { mutableStateOf(step) }

    Dialog(onDismissRequest = onDismissRequest) {
        val focusManager = LocalFocusManager.current
        UnevenCard {
            BodyText("tasks")
            TextInputField(
                modifier = Modifier.fillMaxWidth(),
                label = "headline",
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Next) }
                ),
                value = currentStep.title,
                onValueChange = { currentStep = currentStep.copy(title = it) },
                enabled = true
            )
            MultilineTextInputField(
                modifier = Modifier.fillMaxWidth(),
                label = "detailed description",
                text = currentStep.description ?: "",
                maxChars = null,
                onValueChange = { currentStep = currentStep.copy(description = it) },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
            )

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                BaseButton(text = confirmText, onClick = { onConfirm(currentStep) })
            }
        }
    }
}

@Composable
fun PermissionDialog(
    permission: MaintainerPermission,
    isPermanentlyDeclined: Boolean,
    onDismiss: () -> Unit,
    onAccept: () -> Unit,
    onGoToAppSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(12),
        buttons = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Divider()
                Text(
                    text = if (isPermanentlyDeclined) {
                        "Grant permission"
                    } else {
                        "OK"
                    },
                    style = typography.h6,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (isPermanentlyDeclined) {
                                onGoToAppSettingsClick()
                            } else {
                                onAccept()
                            }
                        }
                        .padding(16.dp)
                )
            }
        },
        title = {
            HeadlineSlim(text = "Permission required")
        },
        text = {
            BodyText(
                text = if (isPermanentlyDeclined) {
                    permission.permanentDeclinedText
                } else {
                    permission.description
                }
            )
        },
        modifier = modifier
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = false)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = false)
@Composable
fun PreviewDialog() {
    MaintainerTheme {
//        ConfirmDialog()
        AddStepDialog(step = DEFAULT_STEP)
    }
}
