/*
 * Created by Christopher Becker on 24/05/2023, 10:45
 * Copyright (c) 2023. All rights reserved.
 * Last modified 24/05/2023, 10:45
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

package de.chrisbecker386.maintainer.ui.screens.home.machine

import de.chrisbecker386.maintainer.R
import de.chrisbecker386.maintainer.data.entity.Machine
import de.chrisbecker386.maintainer.data.entity.relation.TaskWithStepsCompletes
import de.chrisbecker386.maintainer.ui.model.ShortStatusState

data class SingleMachineState(
    val machine: Machine = Machine(
        id = 0,
        title = "",
        subtitle = "",
        imageRes = R.drawable.question_mark_48px,
        section = 0
    ),
    val shortStatus: ShortStatusState = ShortStatusState(0, 0),
    val openTasks: List<TaskWithStepsCompletes> = emptyList(),
    val closedTasks: List<TaskWithStepsCompletes> = emptyList()
)
