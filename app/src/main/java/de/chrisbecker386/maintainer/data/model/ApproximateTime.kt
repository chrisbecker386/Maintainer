/*
 * Created by Christopher Becker on 28/04/2023, 06:39
 * Copyright (c) 2023. All rights reserved.
 * Last modified 28/04/2023, 06:39
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

import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

enum class ApproximateTime(private val minutes: Int, val max: Int) {
    MIN_5(5, 7),
    MIN_10(10, 13),
    MIN_15(15, 18),
    MIN_20(20, 25),
    MIN_30(30, 38),
    MIN_45(45, 53),
    HOUR_1(60, 75),
    HOUR_1_30(90, 105),
    HOUR_2(120, 135),
    HOUR_2_30(150, 165),
    HOUR_3(180, 180);

    val approxAsString: String
        get() = "approx. ${
        if (max >= 60) {
            "${(minutes.toDouble() / 60).hours}"
        } else {
            "${minutes.toDouble().minutes}"
        }
        }"
}

fun Int.getApproximateTime(): ApproximateTime {
    return when {
        this <= ApproximateTime.MIN_5.max -> ApproximateTime.MIN_5
        this <= ApproximateTime.MIN_10.max -> ApproximateTime.MIN_10
        this <= ApproximateTime.MIN_15.max -> ApproximateTime.MIN_15
        this <= ApproximateTime.MIN_20.max -> ApproximateTime.MIN_20
        this <= ApproximateTime.MIN_30.max -> ApproximateTime.MIN_30
        this <= ApproximateTime.MIN_45.max -> ApproximateTime.MIN_45
        this <= ApproximateTime.HOUR_1.max -> ApproximateTime.HOUR_1
        this <= ApproximateTime.HOUR_1_30.max -> ApproximateTime.HOUR_1_30
        this <= ApproximateTime.HOUR_2.max -> ApproximateTime.HOUR_2
        this <= ApproximateTime.HOUR_2_30.max -> ApproximateTime.HOUR_2_30
        this <= ApproximateTime.HOUR_3.max -> ApproximateTime.HOUR_3
        else -> ApproximateTime.MIN_20
    }
}
