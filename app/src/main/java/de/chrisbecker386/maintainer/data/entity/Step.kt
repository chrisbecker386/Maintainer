/*
 * Created by Christopher Becker on 13/05/2023, 07:53
 * Copyright (c) 2023. All rights reserved.
 * Last modified 13/05/2023, 07:53
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

package de.chrisbecker386.maintainer.data.entity

import androidx.annotation.DrawableRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import de.chrisbecker386.maintainer.data.model.RepeatCycle

@Entity(
    tableName = "steps",
    foreignKeys = [
        ForeignKey(
            entity = Task::class,
            parentColumns = ["task_id"],
            childColumns = ["step_fk_task_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("step_fk_task_id")]
)

data class Step(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "step_id")
    val id: Int = 0,
    @ColumnInfo(name = "step_order_number")
    val order: Int,
    @ColumnInfo(name = "step_title")
    val title: String,
    @ColumnInfo(name = "step_image")
    @DrawableRes
    val imageRes: Int? = null,
    @ColumnInfo(name = "step_description")
    val description: String? = null,
    @ColumnInfo(name = "step_completed")
    val completedDate: Long? = null,
    @ColumnInfo(name = "step_fk_task_id")
    val taskId: Int = 0
) {
    fun isValid(repeatCycle: RepeatCycle): Boolean {
        return repeatCycle.isValid(completedDate)
    }
}
