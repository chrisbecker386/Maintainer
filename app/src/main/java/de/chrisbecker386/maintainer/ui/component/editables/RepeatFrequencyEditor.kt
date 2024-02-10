/*
 * Created by Christopher Becker on 30/01/2024, 21:06
 * Copyright (c) 2024. All rights reserved.
 * Last modified 30/01/2024, 21:06
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
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import de.chrisbecker386.maintainer.data.model.RepeatFrequency
import de.chrisbecker386.maintainer.data.utility.toRepeatFrequency
import de.chrisbecker386.maintainer.ui.component.IntervalPicker
import de.chrisbecker386.maintainer.ui.component.MaintainerDatePickerDialog
import de.chrisbecker386.maintainer.ui.component.MaintainerTimePickerDialog
import de.chrisbecker386.maintainer.ui.component.basic.NumberPickerVertical
import de.chrisbecker386.maintainer.ui.theme.BUTTON_CORNER_SHAPE
import de.chrisbecker386.maintainer.ui.theme.BaseGray
import de.chrisbecker386.maintainer.ui.theme.DIM_NO
import de.chrisbecker386.maintainer.ui.theme.DIM_S
import de.chrisbecker386.maintainer.ui.theme.DIM_S_PLUS
import de.chrisbecker386.maintainer.ui.theme.DIM_XS
import de.chrisbecker386.maintainer.ui.theme.DIM_XXXXS
import de.chrisbecker386.maintainer.ui.theme.DisabledGray
import de.chrisbecker386.maintainer.ui.theme.MaintainerTheme
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * Composable for editing repeat frequency settings.
 *
 * @param modifier Modifier for styling and layout adjustments.
 * @param title Title of the element
 * @param startDateTime Initial epoch seconds for the date and time.
 * @param repeatFrequencyAndTact Initial repeat frequency in milliseconds.
 * @param onDateTimeChange Lambda invoked when the date and time change.
 * @param onRepeatFrequencyAndTactChange Lambda invoked when the repeat frequency changes.
 * @param content Optional composable lambda for additional content.
 */
@Composable
fun RepeatFrequencyEditor(
    modifier: Modifier = Modifier,
    enable: Boolean = true,
    title: String = "repeat frequency",
    startDateTime: Long = LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond(),
    repeatFrequencyAndTact: Pair<Long?, Int> = Pair(null, 1),
    onDateTimeChange: (Long) -> Unit = {},
    onRepeatFrequencyAndTactChange: (Pair<Long, Int>) -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    val zoneOffSet = ZoneId.systemDefault().rules.getOffset(LocalDateTime.now())

    var dateState: LocalDate by rememberSaveable {
        mutableStateOf(
            LocalDateTime.ofEpochSecond(
                startDateTime,
                0,
                zoneOffSet
            ).toLocalDate()
        )
    }

    var timeState: LocalTime by rememberSaveable {
        mutableStateOf(
            LocalDateTime.ofEpochSecond(
                startDateTime,
                0,
                zoneOffSet
            ).toLocalTime()
        )
    }

    var localRepeatFrequency by rememberSaveable {
        mutableStateOf(repeatFrequencyAndTact.first?.toRepeatFrequency() ?: RepeatFrequency.WEEKLY)
    }
    var repeatFrequencyCount by rememberSaveable { mutableIntStateOf(1) }

    var showTimePicker by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }

    fun getFormattedTime(localTime: LocalTime): String {
        val timeFormat = DateTimeFormatter.ofPattern("HH:mm")
        return localTime.format(timeFormat)
    }

    fun getFormattedDate(localDate: LocalDate): String {
        val dateFormat = DateTimeFormatter.ofPattern("EEE, dd. MMM")
        return localDate.format(dateFormat)
    }

    fun getAsEpochSeconds(localDate: LocalDate, localTime: LocalTime): Long =
        LocalDateTime.of(localDate, localTime).atZone(ZoneId.systemDefault()).toEpochSecond()

    Card(
        modifier = modifier
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
        ),
        backgroundColor = if (enable) {
            colors.background
        } else {
            BaseGray
        }

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
                text = title,
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
                horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = getFormattedDate(dateState),
                    style = MaterialTheme.typography.h2,
                    color = MaterialTheme.colors.onBackground
                )
                Button(
                    enabled = enable,
                    onClick = { showDatePicker = !showDatePicker },
                    shape = RoundedCornerShape(BUTTON_CORNER_SHAPE),
                    colors = ButtonDefaults.buttonColors(disabledBackgroundColor = DisabledGray)
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
                horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row {
                    Text(
                        text = if (repeatFrequencyCount > 1) "$repeatFrequencyCount." else "",
                        style = MaterialTheme.typography.h2,
                        color = MaterialTheme.colors.onBackground
                    )
                    Text(
                        text = localRepeatFrequency.text,
                        style = MaterialTheme.typography.h2,
                        color = MaterialTheme.colors.onBackground
                    )
                }
                NumberPickerVertical(
                    enable = enable,
                    value = repeatFrequencyCount,
                    onValueChange = {
                        val newCount = repeatFrequencyCount + it
                        repeatFrequencyCount = newCount
                        onRepeatFrequencyAndTactChange(
                            Pair(
                                localRepeatFrequency.value,
                                newCount
                            )
                        )
                    }
                )
            }
            IntervalPicker(
                modifier = Modifier.fillMaxWidth(),
                enable = enable,
                interval = localRepeatFrequency,
                onValueChange = {
                    localRepeatFrequency = it
                    onRepeatFrequencyAndTactChange(Pair(it.value, 1))
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
                horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = getFormattedTime(timeState),
                    style = MaterialTheme.typography.h2,
                    color = MaterialTheme.colors.onBackground
                )
                Button(
                    enabled = enable,
                    onClick = { showTimePicker = !showTimePicker },
                    shape = RoundedCornerShape(25),
                    colors = ButtonDefaults.buttonColors(disabledBackgroundColor = DisabledGray)
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
                        onDateTimeChange(getAsEpochSeconds(it, timeState))
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
                        onDateTimeChange(getAsEpochSeconds(dateState, it))
                        showTimePicker = false
                    },
                    onDismissRequest = { showTimePicker = false }
                )
            }
            content()
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewRepeatFrequencyEditor() {
    MaintainerTheme {
        RepeatFrequencyEditor(enable = false)
    }
}
