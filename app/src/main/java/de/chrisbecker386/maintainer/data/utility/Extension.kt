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

import android.icu.util.Calendar
import de.chrisbecker386.maintainer.data.model.RepeatFrequency

fun Long.toFormatDateString(format: SimpleDateType): String {
    val cal = Calendar.getInstance()
    cal.timeInMillis = this
    return format.getSimpleDateFormat().format(cal).toString()
}

fun Long.toRepeatFrequency(): RepeatFrequency {
    return when (this) {
        in (0L..1000L) -> RepeatFrequency.SECONDLY
        in (1001L..60000L) -> RepeatFrequency.MINUTELY
        in (60001L..3600000L) -> RepeatFrequency.HOURLY
        in (3600001L..86400000L) -> RepeatFrequency.DAILY
        in (86400001L..604800000L) -> RepeatFrequency.WEEKLY
        in (604800001L..2628000000L) -> RepeatFrequency.MINUTELY
        else -> RepeatFrequency.YEARLY
    }
}
