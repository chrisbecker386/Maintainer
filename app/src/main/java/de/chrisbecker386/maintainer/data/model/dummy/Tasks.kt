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

val devTasks = listOf(
    Task(
        id = 1,
        title = "clean water tank",
        subtitle = null,
        imageRes = R.drawable.kettle_48px,
        duration = 5,
        machineId = 1
    ),
    Task(
        id = 2,
        title = "unclogging",
        subtitle = null,
        imageRes = R.drawable.ic_launcher_foreground,
        duration = 5,
        machineId = 1
    ),
    Task(
        id = 3,
        title = "carTask1",
        subtitle = null,
        imageRes = R.drawable.question_mark_48px,
        duration = 5,
        machineId = 2
    )

)
