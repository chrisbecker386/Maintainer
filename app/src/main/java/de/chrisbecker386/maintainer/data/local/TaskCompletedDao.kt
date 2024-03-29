/*
 * Created by Christopher Becker on 15/05/2023, 11:36
 * Copyright (c) 2023. All rights reserved.
 * Last modified 15/05/2023, 11:36
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
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import de.chrisbecker386.maintainer.data.entity.TaskCompletedDate
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskCompletedDao {
    @Insert
    suspend fun addTaskCompleted(taskCompletedDate: TaskCompletedDate)

    @Update
    suspend fun updateTaskCompleted(taskCompletedDate: TaskCompletedDate)

    @Delete
    suspend fun deleteTaskCompleted(taskCompletedDate: TaskCompletedDate)

    @Transaction
    @Query("SELECT * FROM tasks_completed_dates WHERE task_completed_fk_task_id = :taskId")
    fun getCompletedTaskFlow(taskId: Int): Flow<List<TaskCompletedDate>>

    @Transaction
    @Query("SELECT * FROM tasks_completed_dates WHERE task_completed_fk_task_id = :taskId")
    suspend fun getCompletedTask(taskId: Int): List<TaskCompletedDate>
}
