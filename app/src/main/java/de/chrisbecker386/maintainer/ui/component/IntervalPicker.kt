/*
 * Created by Christopher Becker on 27/12/2023, 10:04
 * Copyright (c) 2023. All rights reserved.
 * Last modified 27/12/2023, 10:04
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
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import de.chrisbecker386.maintainer.data.model.RepeatFrequency
import de.chrisbecker386.maintainer.data.model.RepeatFrequency.Companion.entries
import de.chrisbecker386.maintainer.ui.component.basic.StepsSlider
import de.chrisbecker386.maintainer.ui.theme.MaintainerTheme

@Composable
fun IntervalPicker(
    modifier: Modifier = Modifier,
    withHeader: Boolean = false,
    interval: RepeatFrequency = RepeatFrequency.WEEKLY,
    onEvent: (RepeatFrequency) -> Unit = {}
) {
    var currentRepeatFrequency by remember {
        mutableStateOf(interval)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        if (withHeader) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = currentRepeatFrequency.text,
                style = typography.h2,
                color = colors.onBackground
            )
        }
        StepsSlider(
            modifier = Modifier.fillMaxWidth(),
            items = entries,
            index = RepeatFrequency.getIndex(interval),
            onValueChange = {
                it as RepeatFrequency
                currentRepeatFrequency = it
                onEvent(it)
            }
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun PreviewIntervalPicker() {
    MaintainerTheme {
        IntervalPicker(
            interval = RepeatFrequency.WEEKLY,
            modifier = Modifier.background(color = colors.background)
        )
    }
}
