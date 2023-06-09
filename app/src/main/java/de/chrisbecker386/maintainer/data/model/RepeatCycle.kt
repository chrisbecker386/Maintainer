/*
 * Created by Christopher Becker on 19/04/2023, 10:46
 * Copyright (c) 2023. All rights reserved.
 * Last modified 19/04/2023, 10:46
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

package de.chrisbecker386.maintainer.data.model

import android.icu.util.Calendar
import java.time.LocalDate

data class RepeatCycle(
    val frequency: RepeatFrequency,
    val tact: Int
) {
    fun inMillis(): Long {
        return this.frequency.inMillis() * tact.toLong()
    }

    fun isValid(timeInMillis: Long?): Boolean {
        if (timeInMillis == null) return false

        val now = Calendar.getInstance().timeInMillis
        val lastPlusFrequency = this.inMillis() + timeInMillis

        return lastPlusFrequency > now
    }
}

// TODO has to removed
fun RepeatCycle.getNewCycleStart(date: LocalDate): LocalDate {
    val longTact = tact.toLong()
    return when (frequency) {
        RepeatFrequency.DAILY -> date.plusDays(longTact)
        RepeatFrequency.WEEKLY -> date.plusWeeks(longTact)
        RepeatFrequency.MONTHLY -> date.plusMonths(longTact)
        RepeatFrequency.YEARLY -> date.plusYears(longTact)
        RepeatFrequency.MINUTELY -> TODO()
        RepeatFrequency.HOURLY -> TODO()
        RepeatFrequency.SECONDLY -> TODO()
    }
}

// TODO has to removed
fun RepeatCycle.getLastCycleStartCalculated(date: LocalDate): LocalDate {
    val longTact = tact.toLong()
    return when (frequency) {
        RepeatFrequency.DAILY -> date.minusDays(longTact)
        RepeatFrequency.WEEKLY -> date.minusWeeks(longTact)
        RepeatFrequency.MONTHLY -> date.minusMonths(longTact)
        RepeatFrequency.YEARLY -> date.minusYears(longTact)
        RepeatFrequency.MINUTELY -> TODO()
        RepeatFrequency.HOURLY -> TODO()
        RepeatFrequency.SECONDLY -> TODO()
    }
}

// TODO has to removed
fun RepeatCycle.isCycleExpired(date: LocalDate): Boolean {
    val today = LocalDate.now()
    return today.isBefore(getNewCycleStart(date))
}

// TODO has to removed
fun RepeatCycle.isCycleValid(date: LocalDate): Boolean {
    val today = LocalDate.now()
    val newCycleStartDate = getNewCycleStart(date = date)
    return today.isAfter(newCycleStartDate)
}
