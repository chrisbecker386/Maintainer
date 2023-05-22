/*
 * Created by Christopher Becker on 23/05/2023, 18:29
 * Copyright (c) 2023. All rights reserved.
 * Last modified 23/05/2023, 18:29
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

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat

enum class SimpleDateType {
    FULL_DATE_AND_TIME,
    FULL_DATE,
    FULL_TIME;

    @SuppressLint("SimpleDateFormat")
    fun getSimpleDateFormat(): SimpleDateFormat {
        return when (this) {
            FULL_DATE_AND_TIME -> SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS")
            FULL_DATE -> SimpleDateFormat("yyyy-MM-dd")
            FULL_TIME -> SimpleDateFormat("HH:mm:ss:SSS")
        }
    }
}
