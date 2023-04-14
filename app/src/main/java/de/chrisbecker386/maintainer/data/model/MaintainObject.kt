/*
 * Created by Christopher Becker on 13/04/2023, 17:40
 * Copyright (c) 2023. All rights reserved.
 * Last modified 13/04/2023, 17:40
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

package de.chrisbecker386.maintainer.data.model

import androidx.annotation.DrawableRes

data class MaintainObject(
    val id: Int,
    val title: String,
    @DrawableRes
    val graphic: Int? = null,
    val tasks: List<TaskObject> = emptyList()
)

fun MaintainObject.getNumberOfTasks(): Int = tasks.size

fun MaintainObject.getNumberOfOpenTask() = getOpenTasks().size

fun MaintainObject.getNumberOfClosedTask() = getClosedTasks().size

fun MaintainObject.getTasks() = tasks

fun MaintainObject.getOpenTasks(): List<TaskObject> {
    val openTasks: MutableList<TaskObject> = mutableListOf()
    tasks.forEach { task ->
        if (task.repeatCycle.isCycleValid(task.getLastPerformedDate())) openTasks.add(task)
    }
    return openTasks.toList()
}

fun MaintainObject.getClosedTasks(): List<TaskObject> {
    val closeTasks: MutableList<TaskObject> = mutableListOf()
    tasks.forEach { task ->
        if (task.repeatCycle.isCycleExpired(task.getLastPerformedDate())) closeTasks.add(task)
    }
    return closeTasks.toList()
}

fun MaintainObject.getMaintainStats() = listOf(
    Triple("Total", getNumberOfTasks(), getTasks()),
    Triple("Open", getNumberOfOpenTask(), getOpenTasks()),
    Triple("Closed", getNumberOfClosedTask(), getClosedTasks())
)


