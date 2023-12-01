/*
 * Created by Christopher Becker on 15/05/2023, 10:32
 * Copyright (c) 2023. All rights reserved.
 * Last modified 15/05/2023, 10:32
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

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import de.chrisbecker386.maintainer.data.entity.Step
import de.chrisbecker386.maintainer.data.entity.Task
import de.chrisbecker386.maintainer.data.entity.relation.TaskWithPreconditionsSteps
import de.chrisbecker386.maintainer.data.entity.relation.TaskWithPreconditionsStepsCompletes
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Upsert
    suspend fun addTask(task: Task)

    @Upsert
    suspend fun addTasks(tasks: List<Task>)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun removeTask(task: Task)

    @Transaction
    @Query("SELECT * FROM tasks WHERE task_id = :taskId")
    fun getTask(taskId: Int): Flow<Task>

    @Transaction
    @Query("SELECT * FROM tasks")
    fun getAllTasks(): Flow<List<Task>>

    @Transaction
    @Query("SELECT * FROM steps WHERE step_fk_task_id = :taskId")
    fun getStepsForTask(taskId: Int): Flow<List<Step>>

    @Transaction
    @Query("SELECT * FROM tasks")
    fun getAllTaskWithPreconditionsStepsCompletes(): Flow<List<TaskWithPreconditionsStepsCompletes>>

    @Transaction
    @Query("SELECT * FROM tasks WHERE task_id = :taskId")
    fun getTaskWithPreconditionsSteps(taskId: Int): Flow<TaskWithPreconditionsSteps>

    @Transaction
    @Query("SELECT * FROM tasks WHERE task_id = :taskId")
    fun getTaskWithPreconditionsStepsCompletes(taskId: Int): Flow<TaskWithPreconditionsStepsCompletes>

    @Transaction
    @Query("SELECT * FROM tasks WHERE task_fk_machine_id = :machineId")
    fun getTasksForMachineWithPreconditionsStepsCompletes(machineId: Int): Flow<List<TaskWithPreconditionsStepsCompletes>>

    @Transaction
    @Query(
        """SELECT * FROM tasks t 
        LEFT JOIN tasks_completed_dates tcd  
        ON t.task_id = tcd.task_completed_fk_task_id 
        WHERE (tcd.task_completed_id IS NULL 
        OR (tcd.task_completed_date IS NOT NULL 
        AND tcd.task_completed_date < :moment - t.task_tact * t.task_repeat_frequency)) 
        AND t.task_fk_machine_id =:machineId"""
    )
    fun getOpenTaskForMachine(machineId: Int, moment: Long): Flow<List<Task>>

    @Transaction
    @Query(
        """SELECT COUNT(*) FROM  tasks t 
        LEFT JOIN tasks_completed_dates tcd  
        ON t.task_id = tcd.task_completed_fk_task_id 
        WHERE (tcd.task_completed_id IS NULL 
        OR (tcd.task_completed_date IS NOT NULL 
        AND tcd.task_completed_date < :moment - t.task_tact * t.task_repeat_frequency))"""
    )
    fun getNumberOfOpenTasks(moment: Long): Flow<Int>

    @Transaction
    @Query("""SELECT COUNT(*) FROM tasks""")
    fun getNumberOfAllTasks(): Flow<Int>

    @Transaction
    @Query(
        """SELECT COUNT(task_id) FROM tasks t 
        JOIN machines m ON t.task_fk_machine_id = m.machine_id 
        JOIN sections s ON m.machine_fk_section_id = s.section_id
        WHERE m.machine_id NOT NULL AND s.section_id =:sectionId"""
    )
    fun getNumberOfAllTasksBySection(sectionId: Int): Flow<Int>

    @Transaction
    @Query(
        """SELECT COUNT(task_id) FROM tasks t 
        JOIN machines m ON t.task_fk_machine_id = m.machine_id 
        JOIN sections s ON m.machine_fk_section_id = s.section_id
        LEFT JOIN tasks_completed_dates tcd ON t.task_id = tcd.task_completed_fk_task_id 
        WHERE m.machine_id NOT NULL AND s.section_id =:sectionId AND ((tcd.task_completed_id IS NULL 
        OR (tcd.task_completed_date IS NOT NULL 
        AND tcd.task_completed_date < :moment - t.task_tact * t.task_repeat_frequency)))"""
    )
    fun getNumberOfAllOpenTasksBySection(sectionId: Int, moment: Long): Flow<Int>
}
