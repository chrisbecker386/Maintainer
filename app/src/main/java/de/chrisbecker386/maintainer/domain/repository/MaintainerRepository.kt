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

import de.chrisbecker386.maintainer.data.entity.Machine
import de.chrisbecker386.maintainer.data.entity.Section
import de.chrisbecker386.maintainer.data.entity.Step
import de.chrisbecker386.maintainer.data.entity.Task
import de.chrisbecker386.maintainer.data.entity.TaskCompletedDate
import de.chrisbecker386.maintainer.data.entity.relation.TaskWithPreconditionsStepsCompletes
import kotlinx.coroutines.flow.Flow

interface MaintainerRepository {

    suspend fun addSection(section: Section)
    suspend fun addSections(sections: List<Section>)
    fun getSection(sectionId: Int): Flow<Section>
    fun getAllSections(): Flow<List<Section>>
    fun getMachines(sectionId: Int): Flow<List<Machine>>
    suspend fun addMachine(machine: Machine)
    suspend fun addMachines(machines: List<Machine>)
    suspend fun removeMachine(machine: Machine)
    suspend fun removeAllMachines()
    fun getMachine(machineId: Int): Flow<Machine>
    fun getNextMachine(moment: Long): Flow<Machine?>
    suspend fun addTask(task: Task)
    suspend fun addTasks(tasks: List<Task>)
    suspend fun updateTask(task: Task)
    suspend fun removeTask(task: Task)
    fun getTaskFlow(taskId: Int): Flow<Task>
    suspend fun getTask(taskId: Int): Task
    fun getAllTasks(): Flow<List<Task>>
    fun getTasksFlow(machineId: Int): Flow<List<Task>>

    suspend fun getLastTaskId(): Int

    fun getTaskWithPreconditionsStepsCompletes(taskId: Int): Flow<TaskWithPreconditionsStepsCompletes>
    fun getAllTaskWithPreconditionsStepsCompletes(): Flow<List<TaskWithPreconditionsStepsCompletes>>
    fun getTasksForMachineWithPreconditionsStepsCompletes(machineId: Int): Flow<List<TaskWithPreconditionsStepsCompletes>>
    fun getOpenTaskForMachine(machineId: Int, moment: Long): Flow<List<Task>>
    fun getNumberOfOpenTasks(moment: Long): Flow<Int>
    fun getNumberOfAllTasks(): Flow<Int>
    fun getNumberOfAllTasksBySection(sectionId: Int): Flow<Int>
    fun getNumberOfAllOpenTasksBySection(sectionId: Int, moment: Long): Flow<Int>
    suspend fun addTaskComplete(taskCompletedDate: TaskCompletedDate)
    suspend fun updateTaskCompleted(taskCompletedDate: TaskCompletedDate)
    suspend fun deleteTaskCompleted(taskCompletedDate: TaskCompletedDate)
    fun getCompletedTaskFlow(taskId: Int): Flow<List<TaskCompletedDate>>
    suspend fun getCompletedTask(taskId: Int): List<TaskCompletedDate>
    suspend fun addStep(step: Step)
    suspend fun addSteps(steps: List<Step>)
    suspend fun updateStep(step: Step)
    suspend fun removeSteps(steps: List<Step>)
    suspend fun removeStepsByTaskId(taskId: Int)
    suspend fun removeAllSteps()
    fun getStepsFlow(taskId: Int): Flow<List<Step>>

    suspend fun getSteps(taskId: Int): List<Step>
}
