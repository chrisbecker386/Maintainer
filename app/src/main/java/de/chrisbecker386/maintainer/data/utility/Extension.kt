/*
 * Created by Christopher Becker on 23/05/2023, 18:30
 * Copyright (c) 2023. All rights reserved.
 * Last modified 23/05/2023, 18:30
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

package de.chrisbecker386.maintainer.data.utility

import android.content.BroadcastReceiver
import android.content.Context
import android.icu.util.Calendar
import android.text.format.DateUtils
import androidx.core.content.ContextCompat.getString
import de.chrisbecker386.maintainer.R
import de.chrisbecker386.maintainer.data.model.RepeatFrequency
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Math.abs
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun Long.toFormatDateString(format: SimpleDateType = SimpleDateType.FULL_DATE_EUROPE): String {
    val cal = Calendar.getInstance()
    cal.timeInMillis = this
    return format.getSimpleDateFormat().format(cal).toString()
}

fun Long.toTimeIndicationOrFormat(
    context: Context,
    format: SimpleDateType = SimpleDateType.FULL_DATE_EUROPE

): String {
    return when {
        DateUtils.isToday(this) -> getString(context, R.string.today)
        DateUtils.isToday(this + DateUtils.DAY_IN_MILLIS) -> getString(context, R.string.yesterday)
        DateUtils.isToday(this + (2 * DateUtils.DAY_IN_MILLIS)) -> getString(
            context,
            R.string.day_before_yesterday
        )

        DateUtils.isToday(this - DateUtils.DAY_IN_MILLIS) -> getString(context, R.string.tomorrow)
        DateUtils.isToday(this - (2 * DateUtils.DAY_IN_MILLIS)) -> getString(
            context,
            R.string.day_after_tomorrow
        )

        else -> this.toFormatDateString(format)
    }
}

fun Long.toRepeatFrequency(): RepeatFrequency {
    return when (this) {
        in (0L..1000L) -> RepeatFrequency.SECONDLY
        in (1001L..60000L) -> RepeatFrequency.MINUTELY
        in (60001L..3600000L) -> RepeatFrequency.HOURLY
        in (3600001L..86400000L) -> RepeatFrequency.DAILY
        in (86400001L..604800000L) -> RepeatFrequency.WEEKLY
        in (604800001L..2628333333L) -> RepeatFrequency.MONTHLY // 1/12 of a year
        else -> RepeatFrequency.YEARLY // 365.2421988 days
    }
}

fun Long.getRepeatFrequencyWithTact(): Pair<RepeatFrequency, Int> {
    RepeatFrequency.values().forEach { repeatFrequency ->
        if (this % repeatFrequency.value == 0L) {
            return Pair(repeatFrequency, (this / repeatFrequency.value).toInt())
        }
    }

    return Pair(RepeatFrequency.SECONDLY, (this / RepeatFrequency.SECONDLY.value).toInt())
}

fun Float.sameValueAs(other: Float): Boolean {
    return (abs(this - other) < 0.004)
}

fun Int.isEven(): Boolean = (this % 2) == 1

fun BroadcastReceiver.goAsync(
    context: CoroutineContext = EmptyCoroutineContext,
    block: suspend CoroutineScope.() -> Unit
) {
    val pendingResult = goAsync()
    @OptIn(DelicateCoroutinesApi::class) // Must run globally; there's no teardown callback.
    GlobalScope.launch(context) {
        try {
            block()
        } finally {
            pendingResult.finish()
        }
    }
}
