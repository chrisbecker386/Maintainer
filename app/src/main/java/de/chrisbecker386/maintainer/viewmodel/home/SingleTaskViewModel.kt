/*
 * Created by Christopher Becker on 13/05/2023, 07:38
 * Copyright (c) 2023. All rights reserved.
 * Last modified 13/05/2023, 07:38
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

package de.chrisbecker386.maintainer.viewmodel.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import de.chrisbecker386.maintainer.data.entity.Step
import de.chrisbecker386.maintainer.data.entity.Task
import de.chrisbecker386.maintainer.data.model.dummy.dummyStepsDB
import de.chrisbecker386.maintainer.data.model.dummy.dummyTasksDB
import de.chrisbecker386.maintainer.domain.repository.TaskRepository
import de.chrisbecker386.maintainer.ui.model.ShortStatusState
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class SingleTaskViewModel
@Inject
constructor(
    private val repository: TaskRepository
) :
    ViewModel() {
    init {
        setDBEntries()
    }

    private val _task = MutableStateFlow<Task?>(null)
    val task = _task
    private val _steps = MutableStateFlow<List<Step>>(emptyList())
    val steps = _steps

    private val _shortState = MutableStateFlow(ShortStatusState(0, 0))
    val shortStatus = _shortState


    private fun setDBEntries() {
        CoroutineScope(IO).launch {
            withContext(Dispatchers.Default) {
                repository.upsertTasks(
                    dummyTasksDB
                )
            }
            withContext(Dispatchers.Default) {
                repository.insertSteps(
                    dummyStepsDB
                )
            }
        }
    }

    fun setup(taskId: Int) {
        CoroutineScope(IO).launch {
            getTask(taskId)
            getSteps(taskId)
        }
    }

    private suspend fun getTask(taskId: Int) = _task.emit(repository.getTask(taskId))

    private suspend fun getSteps(taskId: Int) {
        _steps.emit(repository.getSteps(taskId))
        _shortState.emit(ShortStatusState(0, steps.value.size))
    }


}
