/*
 * Created by Christopher Becker on 19/04/2023, 17:17
 * Copyright (c) 2023. All rights reserved.
 * Last modified 19/04/2023, 17:17
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

package de.chrisbecker386.maintainer.data.model.dummy

import de.chrisbecker386.maintainer.data.model.CareObject
import de.chrisbecker386.maintainer.data.model.MachineObject
import de.chrisbecker386.maintainer.data.model.TaskObject

// mocking repository
object DummyData {
    val cares = dummyCares
    private val maintains = dummyMaintains
    private val steps = dummySteps
    private val tasks = dummyTasks

    fun getCareObject(careName: String?): CareObject {
        return cares.first { it.title == careName }
    }

    fun getMaintainObject(maintainName: String?): MachineObject {
        return maintains.first { it.title == maintainName }
    }

    fun getTaskObject(taskName: String?): TaskObject {
        return tasks.first { it.title == taskName }
    }
}
