/*
 * Created by Christopher Becker on 17/04/2023, 12:32
 * Copyright (c) 2023. All rights reserved.
 * Last modified 17/04/2023, 12:32
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

package de.chrisbecker386.maintainer.data.model.dummy

import de.chrisbecker386.maintainer.R
import de.chrisbecker386.maintainer.data.entity.Task
import de.chrisbecker386.maintainer.data.model.RepeatFrequency

val devTasks = listOf(
    Task(
        id = 1,
        title = "clean water tank",
        subtitle = null,
        imageRes = R.drawable.kettle_48px,
        machineId = 1
    ),
    Task(
        id = 2,
        title = "unclogging",
        subtitle = null,
        imageRes = R.drawable.coffee_maker_48px,
        machineId = 1
    ),
    Task(
        id = 3,
        title = "defrosting",
        subtitle = null,
        imageRes = R.drawable.kitchen_48px,
        repeatFrequency = RepeatFrequency.MONTHLY.value,
        tact = 6,
        machineId = 2
    ),
    Task(
        id = 4,
        title = "cleaning the lint trap",
        subtitle = null,
        imageRes = R.drawable.dishwasher_gen_48px,
        repeatFrequency = RepeatFrequency.MONTHLY.value,
        tact = 1,
        machineId = 3
    ),
    Task(
        id = 5,
        title = "check and clean the vent hose",
        subtitle = null,
        imageRes = R.drawable.local_laundry_service_48px,
        repeatFrequency = RepeatFrequency.MONTHLY.value,
        tact = 1,
        machineId = 4
    ),
    Task(
        id = 6,
        title = "cleaning the filter",
        subtitle = null,
        imageRes = R.drawable.blur_circular_48px,
        repeatFrequency = RepeatFrequency.WEEKLY.value,
        tact = 1,
        machineId = 5
    ),
    Task(
        id = 7,
        title = "special salt",
        subtitle = null,
        imageRes = R.drawable.snowing_48px,
        repeatFrequency = RepeatFrequency.MONTHLY.value,
        tact = 2,
        machineId = 5
    ),
    Task(
        id = 8,
        title = "clear rinse",
        subtitle = null,
        imageRes = R.drawable.water_drop_48px,
        repeatFrequency = RepeatFrequency.WEEKLY.value,
        tact = 2,
        machineId = 5
    ),
    Task(
        id = 9,
        title = "cleaning the filter",
        subtitle = null,
        imageRes = R.drawable.blur_circular_48px,
        repeatFrequency = RepeatFrequency.WEEKLY.value,
        tact = 1,
        machineId = 6
    ),
    Task(
        id = 10,
        title = "Backup",
        subtitle = null,
        imageRes = R.drawable.backup_48px,
        repeatFrequency = RepeatFrequency.MONTHLY.value,
        tact = 1,
        machineId = 9
    ),
    Task(
        id = 11,
        title = "check engine oil",
        subtitle = null,
        imageRes = R.drawable.car_engine_48px,
        repeatFrequency = RepeatFrequency.MONTHLY.value,
        tact = 1,
        machineId = 7
    ),
    Task(
        id = 12,
        title = "check tire pressure",
        subtitle = null,
        imageRes = R.drawable.tire_repair_48px,
        repeatFrequency = RepeatFrequency.MONTHLY.value,
        tact = 1,
        machineId = 7
    ),
    Task(
        id = 13,
        title = "check tire pressure",
        subtitle = null,
        imageRes = R.drawable.tire_repair_48px,
        repeatFrequency = RepeatFrequency.MONTHLY.value,
        tact = 1,
        machineId = 8
    ),
    Task(
        id = 14,
        title = "check brakes",
        subtitle = null,
        imageRes = R.drawable.question_mark_48px,
        repeatFrequency = RepeatFrequency.MONTHLY.value,
        tact = 1,
        machineId = 8
    )
)
