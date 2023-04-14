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
import de.chrisbecker386.maintainer.data.model.TaskObject

val dummyTasks = listOf(
    TaskObject(
        id = 1,
        title = "Example Task",
        graphic = R.drawable.espresso_machine,
        steps = dummySteps
    ),
    TaskObject(
        id = 2,
        title = "Example Task Other",
        graphic = null,
        steps = dummySteps
    )

)
