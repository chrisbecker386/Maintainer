/*
 * Created by Christopher Becker on 15/05/2023, 10:37
 * Copyright (c) 2023. All rights reserved.
 * Last modified 15/05/2023, 10:37
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
import kotlinx.coroutines.flow.Flow

@Dao
interface StepDao {
    @Upsert
    suspend fun addStep(step: Step)

    @Upsert
    suspend fun addSteps(steps: List<Step>)

    @Update
    suspend fun updateStep(step: Step)

    @Delete
    suspend fun removeStep(step: Step)

    @Delete
    suspend fun removeSteps(steps: List<Step>)

    @Transaction
    @Query("DELETE FROM steps WHERE step_fk_task_id =:taskId")
    suspend fun removeStepsByTaskId(taskId: Int)

    @Transaction
    @Query("DELETE FROM steps")
    suspend fun removeAllSteps()

    @Transaction
    @Query("SELECT * FROM steps WHERE step_fk_task_id = :taskId")
    fun getStepsFlow(taskId: Int): Flow<List<Step>>

    @Transaction
    @Query("SELECT * FROM steps WHERE step_fk_task_id = :taskId")
    suspend fun getSteps(taskId: Int): List<Step>
}
