/*
 * Created by Christopher Becker on 15/02/2024, 18:41
 * Copyright (c) 2024. All rights reserved.
 * Last modified 15/02/2024, 18:41
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

import de.chrisbecker386.maintainer.data.utility.SimpleDateType
import de.chrisbecker386.maintainer.data.utility.toFormatDateString
import de.chrisbecker386.maintainer.data.utility.toRepeatFrequency

data class AlarmReminder(
    val firstExecutionTime: Long,
    val repeatInterval: Long = RepeatFrequency.HOURLY.inMillis(),
    val title: String,
    val message: String = ""
) {
    companion object {
        fun getInfo(firstExecutionTime: Long, repeatInterval: Long): String =
            "firstExecution: ${(firstExecutionTime * 1000).toFormatDateString(SimpleDateType.FULL_DATE_AND_TIME)}\nrepeatInterval: ${repeatInterval.toRepeatFrequency().text}"
    }
}
