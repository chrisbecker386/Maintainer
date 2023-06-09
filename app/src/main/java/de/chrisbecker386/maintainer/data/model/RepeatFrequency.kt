/*
 * Created by Christopher Becker on 19/04/2023, 10:56
 * Copyright (c) 2023. All rights reserved.
 * Last modified 19/04/2023, 10:56
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

enum class RepeatFrequency {
    SECONDLY,
    MINUTELY,
    HOURLY,
    DAILY,
    WEEKLY,
    MONTHLY,
    YEARLY;

    fun inMillis(): Long {
        return when (this) {
            SECONDLY -> 1000L
            MINUTELY -> 60000L
            HOURLY -> 3600000L
            DAILY -> 86400000L
            WEEKLY -> 604800000L
            MONTHLY -> 2628000000L
            YEARLY -> 31540000000L
        }
    }
}
