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
import de.chrisbecker386.maintainer.data.model.interfaces.ItemObject

data class MachineObject(
    override val id: Int,
    override val title: String,
    @DrawableRes
    override val graphic: Int? = null,
    override val list: List<TaskObject> = emptyList()
) : ItemObject

fun MachineObject.getTasks() = list

fun MachineObject.getOpenTasks(): List<TaskObject> {
    val openTasks: MutableList<TaskObject> = mutableListOf()
    list.forEach { task ->
        if (task.repeatCycle.isCycleValid(task.getLastPerformedDate())) openTasks.add(task)
    }
    return openTasks.toList()
}

fun MachineObject.getClosedTasks(): List<TaskObject> {
    val closeTasks: MutableList<TaskObject> = mutableListOf()
    list.forEach { task ->
        if (task.repeatCycle.isCycleExpired(task.getLastPerformedDate())) closeTasks.add(task)
    }
    return closeTasks.toList()
}

fun MachineObject.getMaintainStats() = listOf(
    Pair("Total", getTasks()),
    Pair("Open", getOpenTasks()),
    Pair("Closed", getClosedTasks())
)
