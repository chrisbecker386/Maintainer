/*
 * Created by Christopher Becker on 15/05/2023, 16:26
 * Copyright (c) 2023. All rights reserved.
 * Last modified 15/05/2023, 16:26
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

package de.chrisbecker386.maintainer.domain.repository

import de.chrisbecker386.maintainer.data.entity.Step
import de.chrisbecker386.maintainer.data.entity.Task
import de.chrisbecker386.maintainer.data.entity.TaskCompletedDate
import de.chrisbecker386.maintainer.data.entity.relation.TaskWithPreconditionsStepsCompletes
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getAllTaskWithWithPreconditionsStepsCompletes(): Flow<List<TaskWithPreconditionsStepsCompletes>>

    fun getTaskWithPreconditionsStepsCompletes(taskId: Int): Flow<TaskWithPreconditionsStepsCompletes>

    suspend fun addTaskComplete(taskCompletedDate: TaskCompletedDate)

    suspend fun updateStepComplete(step: Step)

    suspend fun upsertTask(task: Task)

    suspend fun upsertTasks(tasks: List<Task>)

    suspend fun insertSteps(steps: List<Step>)

    suspend fun removeSteps(steps: List<Step>)

    suspend fun removeAllSteps()

    fun getSteps(taskId: Int): Flow<List<Step>>

    fun getTask(taskId: Int): Flow<Task>
}
