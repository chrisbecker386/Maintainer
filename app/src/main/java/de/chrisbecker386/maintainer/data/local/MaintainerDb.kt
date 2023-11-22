/*
 * Created by Christopher Becker on 09/05/2023, 12:51
 * Copyright (c) 2023. All rights reserved.
 * Last modified 09/05/2023, 12:51
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

package de.chrisbecker386.maintainer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import de.chrisbecker386.maintainer.data.entity.Machine
import de.chrisbecker386.maintainer.data.entity.Precondition
import de.chrisbecker386.maintainer.data.entity.Section
import de.chrisbecker386.maintainer.data.entity.Step
import de.chrisbecker386.maintainer.data.entity.Task
import de.chrisbecker386.maintainer.data.entity.TaskCompletedDate

@Database(
    entities = [
        Section::class,
        Machine::class,
        Task::class,
        Step::class,
        Precondition::class,
        TaskCompletedDate::class
    ],
    version = 10,
    exportSchema = false
)

abstract class MaintainerDb : RoomDatabase() {
    abstract val sectionDao: SectionDao
    abstract val machineDao: MachineDao
    abstract val taskDao: TaskDao
    abstract val stepDao: StepDao
    abstract val preconditionDao: PreconditionDao
    abstract val taskCompletedDao: TaskCompletedDao
}
