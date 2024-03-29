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
import de.chrisbecker386.maintainer.data.entity.relation.TaskWithDetails
import de.chrisbecker386.maintainer.data.entity.relation.TaskWithStepsCompletes
import de.chrisbecker386.maintainer.data.entity.relation.TaskWithTaskCompletedDate
import de.chrisbecker386.maintainer.data.local.MachineDao
import de.chrisbecker386.maintainer.data.local.SectionDao
import de.chrisbecker386.maintainer.data.local.StepDao
import de.chrisbecker386.maintainer.data.local.TaskCompletedDao
import de.chrisbecker386.maintainer.data.local.TaskDao
import de.chrisbecker386.maintainer.domain.repository.MaintainerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MaintainerRepositoryImpl(
    private val sectionDao: SectionDao,
    private val machineDao: MachineDao,
    private val taskDao: TaskDao,
    private val stepDao: StepDao,
    private val taskCompletedDao: TaskCompletedDao
) : MaintainerRepository {

    override suspend fun addSection(section: Section) = sectionDao.addSection(section)

    override suspend fun updateSection(section: Section) = sectionDao.updateSection(section)

    override suspend fun addSections(sections: List<Section>) = sectionDao.addSections(sections)
    override suspend fun getSection(sectionId: Int): Section = sectionDao.getSection(sectionId)

    override fun getSectionFlow(sectionId: Int): Flow<Section> =
        sectionDao.getSectionFlow(sectionId)

    override fun getAllSections(): Flow<List<Section>> = sectionDao.getAllSections()

    override fun getMachines(sectionId: Int): Flow<List<Machine>> =
        sectionDao.getMachinesForSection(sectionId)

    override suspend fun addMachine(machine: Machine) = machineDao.addMachine(machine)

    override suspend fun updateMachine(machine: Machine) = machineDao.updateMachine(machine)

    override suspend fun addMachines(machines: List<Machine>) =
        machineDao.addMachines(machines)

    override suspend fun removeMachine(machine: Machine) = machineDao.removeMachine(machine)

    override suspend fun removeAllMachines() = machineDao.removeAllMachines()

    override suspend fun getMachine(machineId: Int): Machine = machineDao.getMachine(machineId)

    override fun getMachineFlow(machineId: Int): Flow<Machine> =
        machineDao.getMachineFlow(machineId)

    override fun getNextMachine(moment: Long): Flow<Machine?> =
        machineDao.getNextMachine(moment)

    override suspend fun addTask(task: Task) = taskDao.addTask(task)

    override suspend fun addTasks(tasks: List<Task>) = taskDao.addTasks(tasks)

    override suspend fun updateTask(task: Task) = taskDao.updateTask(task)

    override suspend fun removeTask(task: Task) = taskDao.removeTask(task)

    override fun getTaskFlow(taskId: Int): Flow<Task> = taskDao.getTaskFlow(taskId)
    override suspend fun getTask(taskId: Int): Task = taskDao.getTask(taskId)

    override fun getAllTasks(): Flow<List<Task>> = taskDao.getAllTasks()

    override fun getTasksFlow(machineId: Int): Flow<List<Task>> =
        machineDao.getTasksForMachine(machineId)

    override suspend fun getAllOpenTasks(moment: Long): List<Task> = taskDao.getAllOpenTasks(moment)

    override fun getAllOpenTasksFlow(moment: Long): Flow<List<Task>> =
        taskDao.getAllTasksFlow(moment)

    override fun getAllOpenTaskWithLastCompleteDate(): List<TaskWithTaskCompletedDate> =
        taskDao.getAllOpenTaskWithLastCompleteDate()

    override fun getAllOpenTaskWithLastCompleteDateFlow(): Flow<List<TaskWithTaskCompletedDate>> =
        taskDao.getAllOpenTaskWithLastCompleteDateFlow()

    override suspend fun getLastTaskId(): Int = taskDao.getLastTaskId()

    override fun getTaskWithPreconditionsStepsCompletes(taskId: Int) =
        taskDao.getTaskWithStepsCompletes(taskId)

    override fun getAllTaskWithPreconditionsStepsCompletes(): Flow<List<TaskWithStepsCompletes>> =
        taskDao.getAllTaskWithStepsCompletes()

    override fun getTasksForMachineWithPreconditionsStepsCompletes(machineId: Int):
        Flow<List<TaskWithStepsCompletes>> =
        taskDao.getTasksForMachineWithStepsCompletes(machineId)

    override fun getOpenTaskForMachine(machineId: Int, moment: Long): Flow<List<Task>> =
        taskDao.getOpenTaskForMachine(machineId, moment)

    override fun getNumberOfOpenTasksFlow(moment: Long): Flow<Int> =
        taskDao.getNumberOfOpenTasks(moment)

    override fun getNumberOfAllTasks(): Flow<Int> = taskDao.getNumberOfAllTasks()
    override fun getNumberOfAllTasksBySection(sectionId: Int): Flow<Int> =
        taskDao.getNumberOfAllTasksBySection(sectionId)

    override fun getNumberOfAllOpenTasksBySection(sectionId: Int, moment: Long): Flow<Int> =
        taskDao.getNumberOfAllOpenTasksBySection(sectionId, moment)

    override suspend fun getTaskWithDetails(taskId: Int): TaskWithDetails? {
        val task = taskDao.getTask(taskId)
        val machine = machineDao.getMachine(task.machineId)
        val section = machine.section?.let { sectionDao.getSection(it) }
        val completedDates = taskCompletedDao.getCompletedTask(taskId)

        return section?.let { TaskWithDetails(task, machine, it, completedDates) }
    }

    override fun getTaskWithDetailsFlow(taskId: Int): Flow<TaskWithDetails?> = flow {
        val task = taskDao.getTask(taskId)
        val machine = machineDao.getMachine(task.machineId)
        val section = machine.section?.let { sectionDao.getSection(it) }
        val completedDates = taskCompletedDao.getCompletedTask(taskId)
        emit(section?.let { TaskWithDetails(task, machine, it, completedDates) })
    }

    override suspend fun addTaskComplete(taskCompletedDate: TaskCompletedDate) =
        taskCompletedDao.addTaskCompleted(taskCompletedDate)

    override suspend fun updateTaskCompleted(taskCompletedDate: TaskCompletedDate) =
        taskCompletedDao.updateTaskCompleted(taskCompletedDate)

    override suspend fun deleteTaskCompleted(taskCompletedDate: TaskCompletedDate) =
        taskCompletedDao.deleteTaskCompleted(taskCompletedDate)

    override fun getCompletedTaskFlow(taskId: Int): Flow<List<TaskCompletedDate>> =
        taskCompletedDao.getCompletedTaskFlow(taskId)

    override suspend fun getCompletedTask(taskId: Int): List<TaskCompletedDate> =
        taskCompletedDao.getCompletedTask(taskId)

    override suspend fun addStep(step: Step) = stepDao.addStep(step)

    override suspend fun addSteps(steps: List<Step>) = stepDao.addSteps(steps)

    override suspend fun updateStep(step: Step) = stepDao.updateStep(step)

    override suspend fun removeSteps(steps: List<Step>) = stepDao.removeSteps(steps)
    override suspend fun removeStepsByTaskId(taskId: Int) = stepDao.removeStepsByTaskId(taskId)

    override suspend fun removeAllSteps() = stepDao.removeAllSteps()

    override fun getStepsFlow(taskId: Int): Flow<List<Step>> = stepDao.getStepsFlow(taskId)

    override suspend fun getSteps(taskId: Int): List<Step> = stepDao.getSteps(taskId)
}
