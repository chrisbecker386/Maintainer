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
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import de.chrisbecker386.maintainer.data.entity.Step

@Dao
interface StepDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSteps(steps: List<Step>)

    @Insert
    suspend fun insertStep(step: Step)

    @Update
    suspend fun updateStep(step: Step)

    @Delete
    suspend fun deleteStep(step: Step)

    @Delete
    suspend fun removeSteps(steps: List<Step>)

    @Transaction
    @Query("DELETE FROM steps")
    fun removeAllSteps()

    @Transaction
    @Query("SELECT * FROM steps WHERE step_fk_task_id = :taskId")
    suspend fun getStepsForTask(taskId: Int): List<Step>
}
