/*
 * Created by Christopher Becker on 23/02/2024, 20:24
 * Copyright (c) 2024. All rights reserved.
 * Last modified 23/02/2024, 20:24
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
import de.chrisbecker386.maintainer.data.entity.Task
import de.chrisbecker386.maintainer.data.entity.TaskCompletedDate

data class TaskWithTaskCompletedDate(
    @Embedded val task: Task,
    @Relation(
        parentColumn = "task_id",
        entityColumn = "task_completed_fk_task_id"
    )
    val completes: List<TaskCompletedDate?>
)
