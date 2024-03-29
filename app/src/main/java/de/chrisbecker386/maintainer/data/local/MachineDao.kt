/*
 * Created by Christopher Becker on 24/05/2023, 13:11
 * Copyright (c) 2023. All rights reserved.
 * Last modified 24/05/2023, 13:11
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
import de.chrisbecker386.maintainer.data.entity.Machine
import de.chrisbecker386.maintainer.data.entity.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface MachineDao {

    @Upsert
    suspend fun addMachine(machine: Machine)

    @Update
    suspend fun updateMachine(machine: Machine)

    @Upsert
    suspend fun addMachines(machines: List<Machine>)

    @Delete
    suspend fun removeMachine(machine: Machine)

    @Transaction
    @Query("DELETE FROM machines")
    suspend fun removeAllMachines()

    @Transaction
    @Query("SELECT * FROM tasks WHERE task_fk_machine_id = :machineId")
    fun getTasksForMachine(machineId: Int): Flow<List<Task>>

    @Transaction
    @Query("SELECT * FROM machines WHERE machine_id = :machineId")
    suspend fun getMachine(machineId: Int): Machine

    @Transaction
    @Query("SELECT * FROM machines WHERE machine_id = :machineId")
    fun getMachineFlow(machineId: Int): Flow<Machine>

    @Transaction
    @Query(
        """SELECT 
    m.*
FROM machines m
JOIN tasks t ON m.machine_id = t.task_fk_machine_id
LEFT JOIN tasks_completed_dates tcd ON t.task_id = tcd.task_completed_fk_task_id
WHERE tcd.task_completed_id IS NULL OR 
    (tcd.task_completed_date IS NOT NULL AND tcd.task_completed_date < :moment - t.task_tact * t.task_repeat_frequency) LIMIT 1
    """
    )
    fun getNextMachine(moment: Long): Flow<Machine?>
}
