/*
 * Created by Christopher Becker on 27/12/2023, 10:01
 * Copyright (c) 2023. All rights reserved.
 * Last modified 27/12/2023, 10:01
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

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Slider
import androidx.compose.material.SliderColors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import de.chrisbecker386.maintainer.data.utility.sameValueAs
import de.chrisbecker386.maintainer.ui.theme.DIM_S
import de.chrisbecker386.maintainer.ui.theme.MaintainerTheme

@Composable
fun StepsSlider(
    modifier: Modifier = Modifier,
    /*items must contain at least 2 items */
    items: List<Any>,
    index: Int = 0,
    onValueChange: (Any) -> Unit = {},
    stepSliderColors: StepSliderColors = StepSliderColors(
        thumbColor = colors.primary,
        disabledThumbColor = colors.onError,
        activeTrackColor = colors.onBackground,
        inactiveTrackColor = colors.onBackground,
        disabledActiveTrackColor = Color.LightGray,
        disabledInactiveTrackColor = Color.LightGray,
        activeTickColor = colors.background,
        inactiveTickColor = colors.background,
        disabledActiveTickColor = Color.Red,
        disabledInactiveTickColor = Color.Red
    )
) {
    LaunchedEffect(items) {
        if (items.size < 2) throw Exception("list must have at least 2 items")
    }

    fun indexToPosition(index: Int): Float =
        when (index) {
            0 -> 0.0f
            items.size - 1 -> 100.0f
            else -> ((100.0 / (items.size - 1).toDouble()) * index.toDouble()).toFloat()
        }

    fun positionToIndex(value: Float, doubleList: List<Double>): Int {
        val first = doubleList.first { value.sameValueAs(it.toFloat()) }
        return doubleList.indexOf(first)
    }

    fun toDoubleList(items: List<Any>): List<Double> {
        val mList = mutableListOf(0.0)
        val innerTicks = items.size - 1
        for (i in 1..innerTicks) {
            mList.add((100.0 / innerTicks.toDouble() * i.toDouble()))
        }
        return mList.toList()
    }

    var currentIndex by remember { mutableStateOf(index) }
    var stepAsFloat by remember { mutableStateOf(indexToPosition(currentIndex)) }
    val stepAsDoubles by remember { mutableStateOf(toDoubleList(items)) }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Slider(
            modifier = Modifier.fillMaxWidth(),
            value = stepAsFloat,
            onValueChange = { value ->
                stepAsFloat = value
                if (stepAsDoubles.firstOrNull { value.sameValueAs(it.toFloat()) } != null) {
                    val localIndex = positionToIndex(value, stepAsDoubles)
                    currentIndex = localIndex
                    onValueChange(items[localIndex])
                }
            },
            valueRange = 0f..100f,
            steps = if (items.size - 2 >= 0) items.size - 2 else 0,
            colors = stepSliderColors
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            items.forEach { item ->
                Text(item.toString(), style = typography.body1, color = colors.onBackground)
            }
        }
    }
}

class StepSliderColors(
    private val thumbColor: Color,
    private val disabledThumbColor: Color,
    private val activeTrackColor: Color,
    private val inactiveTrackColor: Color,
    private val disabledActiveTrackColor: Color,
    private val disabledInactiveTrackColor: Color,
    private val activeTickColor: Color,
    private val inactiveTickColor: Color,
    private val disabledActiveTickColor: Color,
    private val disabledInactiveTickColor: Color
) : SliderColors {

    @Composable
    override fun thumbColor(enabled: Boolean): State<Color> {
        return rememberUpdatedState(if (enabled) thumbColor else disabledThumbColor)
    }

    @Composable
    override fun trackColor(enabled: Boolean, active: Boolean): State<Color> {
        return rememberUpdatedState(
            if (enabled) {
                if (active) activeTrackColor else inactiveTrackColor
            } else {
                if (active) disabledActiveTrackColor else disabledInactiveTrackColor
            }
        )
    }

    @Composable
    override fun tickColor(enabled: Boolean, active: Boolean): State<Color> {
        return rememberUpdatedState(
            if (enabled) {
                if (active) activeTickColor else inactiveTickColor
            } else {
                if (active) disabledActiveTickColor else disabledInactiveTickColor
            }
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as StepSliderColors

        if (thumbColor != other.thumbColor) return false
        if (disabledThumbColor != other.disabledThumbColor) return false
        if (activeTrackColor != other.activeTrackColor) return false
        if (inactiveTrackColor != other.inactiveTrackColor) return false
        if (disabledActiveTrackColor != other.disabledActiveTrackColor) return false
        if (disabledInactiveTrackColor != other.disabledInactiveTrackColor) return false
        if (activeTickColor != other.activeTickColor) return false
        if (inactiveTickColor != other.inactiveTickColor) return false
        if (disabledActiveTickColor != other.disabledActiveTickColor) return false
        if (disabledInactiveTickColor != other.disabledInactiveTickColor) return false

        return true
    }

    override fun hashCode(): Int {
        var result = thumbColor.hashCode()
        result = 31 * result + disabledThumbColor.hashCode()
        result = 31 * result + activeTrackColor.hashCode()
        result = 31 * result + inactiveTrackColor.hashCode()
        result = 31 * result + disabledActiveTrackColor.hashCode()
        result = 31 * result + disabledInactiveTrackColor.hashCode()
        result = 31 * result + activeTickColor.hashCode()
        result = 31 * result + inactiveTickColor.hashCode()
        result = 31 * result + disabledActiveTickColor.hashCode()
        result = 31 * result + disabledInactiveTickColor.hashCode()
        return result
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewStepsSlider() {
    val testList = listOf(
        Pair(1, "sec"),
        Pair(2, "min"),
        Pair(3, "h"),
        Pair(4, "day"),
        Pair(5, "week")
    )

    fun onEvent(item: Any) {
        Log.d("StepSlider onEventItem", "$item")
    }

    MaintainerTheme {
        Column {
            StepsSlider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(DIM_S),
                index = 2,
                onValueChange = ::onEvent,
                items = testList
            )
        }
    }
}
