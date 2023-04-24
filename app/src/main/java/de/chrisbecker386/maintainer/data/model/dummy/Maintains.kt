/*
 * Created by Christopher Becker on 17/04/2023, 15:50
 * Copyright (c) 2023. All rights reserved.
 * Last modified 17/04/2023, 15:50
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
import de.chrisbecker386.maintainer.data.model.MachineObject

val dummyMaintains = listOf(
    MachineObject(
        id = 1,
        title = "Espresso Machine",
        graphic = R.drawable.espresso_machine,
        list = dummyTasks
    ),
    MachineObject(
        id = 1,
        title = "Washing Machine",
        graphic = null,
        list = dummyTasks
    ),
    MachineObject(
        id = 3,
        title = "Motor",
        graphic = R.drawable.kettle_48px,
        list = dummyTasks
    ),
    MachineObject(
        id = 4,
        title = "Tire",
        graphic = null,
        list = dummyTasks
    )
)