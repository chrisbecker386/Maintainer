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
import de.chrisbecker386.maintainer.data.local.CompletedTaskDao
import de.chrisbecker386.maintainer.data.local.MachineDao
import de.chrisbecker386.maintainer.data.local.PreconditionDao
import de.chrisbecker386.maintainer.data.local.SectionDao
import de.chrisbecker386.maintainer.data.local.StepDao
import de.chrisbecker386.maintainer.data.local.TaskDao
import de.chrisbecker386.maintainer.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class TaskRepositoryImpl(
    private val sectionDao: SectionDao,
    private val machineDao: MachineDao,
    private val taskDao: TaskDao,
    private val stepDao: StepDao,
    private val preconditionDao: PreconditionDao,
    private val completedTaskDao: CompletedTaskDao
) : TaskRepository {
    override fun getAllTaskWithWithPreconditionsStepsCompletes(): Flow<List<TaskWithPreconditionsStepsCompletes>> =
        taskDao.getAllTaskWithPreconditionsStepsCompletes()

    override fun getTaskWithPreconditionsStepsCompletes(taskId: Int) =
        taskDao.getTaskWithPreconditionsStepsCompletes(taskId)

    override suspend fun addTaskComplete(taskCompletedDate: TaskCompletedDate) {
        completedTaskDao.insertCompletedTask(taskCompletedDate)
    }

    override suspend fun updateStepComplete(step: Step) {
        stepDao.updateStep(step)
    }

    override suspend fun upsertTask(task: Task) = taskDao.upsertTask(task)
    override suspend fun upsertTasks(tasks: List<Task>) = taskDao.upsertTasks(tasks)

    override suspend fun insertSteps(steps: List<Step>) = stepDao.insertSteps(steps)
    override suspend fun removeSteps(steps: List<Step>) = stepDao.removeSteps(steps)
    override suspend fun removeAllSteps() = stepDao.removeAllSteps()

    override fun getTask(taskId: Int): Flow<Task> = taskDao.getTaskById(taskId)
    override fun getAllTasks(): Flow<List<Task>> = taskDao.getAllTasks()

    override fun getNextOpenTasks(): Flow<List<Task>> = taskDao.getNextOpenTasks()

    override suspend fun upsertMachine(machine: Machine) = machineDao.upsertMachine(machine)

    override suspend fun insertMachines(machines: List<Machine>) =
        machineDao.insertMachines(machines)

    override suspend fun removeAllMachines() = machineDao.removeAllMachines()

    override fun getTasks(machineId: Int): Flow<List<Task>> =
        machineDao.getTasksForMachine(machineId)

    override fun getMachine(machineId: Int): Flow<Machine> = machineDao.getMachine(machineId)
    override fun getTasksForMachineWithPreconditionsStepsCompletes(machineId: Int):
        Flow<List<TaskWithPreconditionsStepsCompletes>> =
        taskDao.getTasksForMachineWithPreconditionsStepsCompletes(machineId)

    override fun getMachines(sectionId: Int): Flow<List<Machine>> =
        sectionDao.getMachinesForSection(sectionId)

    override fun getAllSections(): Flow<List<Section>> =
        sectionDao.getAllSections()

    override fun getSection(sectionId: Int): Flow<Section> = sectionDao.getSection(sectionId)

    override suspend fun upsertSection(section: Section) {
        sectionDao.upsertSection(section)
    }

    override suspend fun addSections(sections: List<Section>) {
        sectionDao.addSections(sections)
    }

    override fun getSteps(taskId: Int): Flow<List<Step>> = stepDao.getStepsForTask(taskId)
}
