/*
 * Created by Christopher Becker on 15/05/2023, 11:21
 * Copyright (c) 2023. All rights reserved.
 * Last modified 15/05/2023, 11:21
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

package de.chrisbecker386.maintainer.data.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import de.chrisbecker386.maintainer.data.entity.Step
import de.chrisbecker386.maintainer.data.entity.Task
import de.chrisbecker386.maintainer.data.entity.TaskCompletedDate
import de.chrisbecker386.maintainer.data.utility.SimpleDateType
import de.chrisbecker386.maintainer.data.utility.toFormatDateString

data class TaskWithStepsCompletes(
    @Embedded val task: Task,
    @Relation(
        parentColumn = "task_id",
        entityColumn = "step_fk_task_id"
    )
    val steps: List<Step>,
    @Relation(
        parentColumn = "task_id",
        entityColumn = "task_completed_fk_task_id"
    )
    val completes: List<TaskCompletedDate>
) {
    fun isValid(): Boolean = this.task.getRepeatCycle().isValid(getLatestCompleteOrNull())

    private fun getLatestCompleteOrNull(): Long? {
        return if (this.completes.isEmpty()) {
            null
        } else {
            this.completes.maxByOrNull { it.date }?.date
        }
    }

    override fun toString(): String = "\n${this.task.title}, ${
    this.completes.last().date.toFormatDateString(
        SimpleDateType.FULL_DATE_EUROPE
    )
    }"
}
