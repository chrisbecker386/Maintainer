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

package de.chrisbecker386.maintainer.data.local.repository

import de.chrisbecker386.maintainer.data.entity.Machine
import de.chrisbecker386.maintainer.data.entity.Section
import de.chrisbecker386.maintainer.data.entity.Step
import de.chrisbecker386.maintainer.data.entity.Task
import de.chrisbecker386.maintainer.data.entity.TaskCompletedDate
import de.chrisbecker386.maintainer.data.entity.relation.TaskWithPreconditionsStepsCompletes
import de.chrisbecker386.maintainer.data.local.MachineDao
import de.chrisbecker386.maintainer.data.local.PreconditionDao
import de.chrisbecker386.maintainer.data.local.SectionDao
import de.chrisbecker386.maintainer.data.local.StepDao
import de.chrisbecker386.maintainer.data.local.TaskCompletedDao
import de.chrisbecker386.maintainer.data.local.TaskDao
import de.chrisbecker386.maintainer.domain.repository.MaintainerRepository
import kotlinx.coroutines.flow.Flow

class MaintainerRepositoryImpl(
    private val sectionDao: SectionDao,
    private val machineDao: MachineDao,
    private val taskDao: TaskDao,
    private val stepDao: StepDao,
    private val preconditionDao: PreconditionDao,
    private val taskCompletedDao: TaskCompletedDao
) : MaintainerRepository {

    override suspend fun addSection(section: Section) = sectionDao.addSection(section)

    override suspend fun addSections(sections: List<Section>) = sectionDao.addSections(sections)

    override fun getSection(sectionId: Int): Flow<Section> = sectionDao.getSection(sectionId)

    override fun getAllSections(): Flow<List<Section>> = sectionDao.getAllSections()

    override fun getMachines(sectionId: Int): Flow<List<Machine>> =
        sectionDao.getMachinesForSection(sectionId)

    override suspend fun addMachine(machine: Machine) = machineDao.addMachine(machine)

    override suspend fun addMachines(machines: List<Machine>) =
        machineDao.addMachines(machines)

    override suspend fun removeMachine(machine: Machine) = machineDao.removeMachine(machine)

    override suspend fun removeAllMachines() = machineDao.removeAllMachines()

    override fun getMachine(machineId: Int): Flow<Machine> = machineDao.getMachine(machineId)

    override fun getNextMachine(moment: Long): Flow<Machine?> =
        machineDao.getNextMachine(moment)

    override suspend fun addTask(task: Task) = taskDao.addTask(task)

    override suspend fun addTasks(tasks: List<Task>) = taskDao.addTasks(tasks)

    override suspend fun updateTask(task: Task) = taskDao.updateTask(task)

    override suspend fun removeTask(task: Task) = taskDao.removeTask(task)

    override fun getTask(taskId: Int): Flow<Task> = taskDao.getTask(taskId)

    override fun getAllTasks(): Flow<List<Task>> = taskDao.getAllTasks()

    override fun getTasks(machineId: Int): Flow<List<Task>> =
        machineDao.getTasksForMachine(machineId)

    override fun getTaskWithPreconditionsStepsCompletes(taskId: Int) =
        taskDao.getTaskWithPreconditionsStepsCompletes(taskId)

    override fun getAllTaskWithPreconditionsStepsCompletes(): Flow<List<TaskWithPreconditionsStepsCompletes>> =
        taskDao.getAllTaskWithPreconditionsStepsCompletes()

    override fun getTasksForMachineWithPreconditionsStepsCompletes(machineId: Int):
        Flow<List<TaskWithPreconditionsStepsCompletes>> =
        taskDao.getTasksForMachineWithPreconditionsStepsCompletes(machineId)

    override fun getOpenTaskForMachine(machineId: Int, moment: Long): Flow<List<Task>> =
        taskDao.getOpenTaskForMachine(machineId, moment)

    override fun getNumberOfOpenTasks(moment: Long): Flow<Int> =
        taskDao.getNumberOfOpenTasks(moment)

    override fun getNumberOfAllTasks(): Flow<Int> = taskDao.getNumberOfAllTasks()

    override suspend fun addTaskComplete(taskCompletedDate: TaskCompletedDate) =
        taskCompletedDao.addTaskCompleted(taskCompletedDate)

    override suspend fun updateTaskCompleted(taskCompletedDate: TaskCompletedDate) =
        taskCompletedDao.updateTaskCompleted(taskCompletedDate)

    override suspend fun deleteTaskCompleted(taskCompletedDate: TaskCompletedDate) =
        taskCompletedDao.deleteTaskCompleted(taskCompletedDate)

    override fun getCompletedTask(taskId: Int): Flow<List<TaskCompletedDate>> =
        taskCompletedDao.getCompletedTask(taskId)

    override suspend fun addStep(step: Step) = stepDao.addStep(step)

    override suspend fun addSteps(steps: List<Step>) = stepDao.addSteps(steps)

    override suspend fun updateStep(step: Step) = stepDao.updateStep(step)

    override suspend fun removeSteps(steps: List<Step>) = stepDao.removeSteps(steps)

    override suspend fun removeAllSteps() = stepDao.removeAllSteps()

    override fun getSteps(taskId: Int): Flow<List<Step>> = stepDao.getSteps(taskId)
}
