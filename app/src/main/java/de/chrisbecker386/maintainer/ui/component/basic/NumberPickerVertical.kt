/*
 * Created by Christopher Becker on 30/12/2023, 08:32
 * Copyright (c) 2023. All rights reserved.
 * Last modified 30/12/2023, 08:32
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

package de.chrisbecker386.maintainer.ui.component.basic

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import de.chrisbecker386.maintainer.ui.theme.DIM_L
import de.chrisbecker386.maintainer.ui.theme.DIM_L_PLUS
import de.chrisbecker386.maintainer.ui.theme.DIM_M
import de.chrisbecker386.maintainer.ui.theme.DIM_S
import de.chrisbecker386.maintainer.ui.theme.DIM_S_PLUS
import de.chrisbecker386.maintainer.ui.theme.DIM_XXXS
import de.chrisbecker386.maintainer.ui.theme.DisabledGray
import de.chrisbecker386.maintainer.ui.theme.MaintainerTheme

@Composable
fun NumberPickerVertical(
    modifier: Modifier = Modifier,
    enable: Boolean = true,
    step: Int = 1,
    value: Int = 0,
    onValueChange: (Int) -> Unit = {}
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(DIM_S),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Box(
                modifier = Modifier
                    .size(DIM_L)
                    .background(
                        color = if (enable) {
                            colors.primary
                        } else {
                            DisabledGray
                        },
                        shape = RoundedCornerShape(DIM_S_PLUS)
                    )
                    .clickable { if (enable) onValueChange(1) },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Plus $step",
                    colorFilter = ColorFilter.tint(color = colors.background)
                )
            }
            Spacer(modifier = Modifier.padding(DIM_XXXS))
            Box(
                modifier = Modifier
                    .size(DIM_L)
                    .background(
                        color = if (enable) {
                            colors.primary
                        } else {
                            DisabledGray
                        },
                        shape = RoundedCornerShape(DIM_S_PLUS)
                    )
                    .clickable { if (enable) onValueChange(-1) },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    imageVector = Icons.Default.Remove,
                    contentDescription = "Minus $step",
                    colorFilter = ColorFilter.tint(color = colors.background)
                )
            }
        }
    }
}

@Composable
fun NumberPickerHorizontal(
    modifier: Modifier = Modifier,
    step: Int = 1,
    stepMin: Int = 0,
    stepMax: Int = 120,
    value: Int = 0,
    onValueChange: (Int) -> Unit = {}
) {
    var valueCounter by remember { mutableStateOf(value) }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(DIM_S),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(DIM_L_PLUS)
                .background(
                    color = colors.primary,
                    shape = RoundedCornerShape(DIM_M)
                )
                .clickable {
                    valueCounter = maxOf(valueCounter - step, stepMin)
                    onValueChange(valueCounter)
                },
            contentAlignment = Alignment.Center
        ) {
            Image(
                imageVector = Icons.Default.Remove,
                contentDescription = "Minus $step",
                colorFilter = ColorFilter.tint(color = colors.background)
            )
        }
        Text(text = valueCounter.toString(), style = typography.h2, color = colors.onBackground)
        Box(
            modifier = Modifier
                .size(DIM_L_PLUS)
                .background(
                    color = colors.primary,
                    shape = RoundedCornerShape(DIM_M)
                )
                .clickable {
                    valueCounter = minOf(valueCounter + step, stepMax)
                    onValueChange(valueCounter)
                },
            contentAlignment = Alignment.Center
        ) {
            Image(
                imageVector = Icons.Default.Add,
                contentDescription = "Plus $step",
                colorFilter = ColorFilter.tint(color = colors.background)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNumberPicker() {
    MaintainerTheme {
        NumberPickerHorizontal(
            step = 15,
            onValueChange = { Log.d("NumberPicker", "new Value: $it") }
        )
    }
}
