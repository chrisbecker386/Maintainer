/*
 * Created by Christopher Becker on 23/05/2023, 10:56
 * Copyright (c) 2023. All rights reserved.
 * Last modified 23/05/2023, 10:56
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

package de.chrisbecker386.maintainer.ui.tab.home.task

import android.icu.util.Calendar
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.chrisbecker386.maintainer.data.entity.TaskCompletedDate
import de.chrisbecker386.maintainer.domain.repository.TaskRepository
import de.chrisbecker386.maintainer.ui.model.ShortStatusState
import de.chrisbecker386.maintainer.ui.tab.home.task.SingleTaskEvent.StepDone
import de.chrisbecker386.maintainer.ui.tab.home.task.SingleTaskEvent.TaskDone
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingleTaskViewModel
@Inject
constructor(
    private val repository: TaskRepository,
    savedStateHandle: SavedStateHandle
) :
    ViewModel() {

    private val _task = repository.getTask(checkNotNull(savedStateHandle.get<Int>("task_type")))

    private val _steps = _task.flatMapLatest { task -> repository.getSteps(task.id) }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        emptyList()
    )

    private val _state = MutableStateFlow(SingleTaskState())

    private val _shortStatus = combine(_steps, _task) { steps, task ->
        val numerator = steps.filter { it.isValid(task.getRepeatCycle()) }.size
        val denominator = steps.size
        ShortStatusState(numerator, denominator)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), ShortStatusState(0, 0))

    private val _isTaskDone = combine(_task, _steps) { task, steps ->
        if (steps.isEmpty()) {
            false
        } else {
            steps.filter { step -> step.isValid(task.getRepeatCycle()) }.size == steps.size
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    val state = combine(
        _state,
        _task,
        _shortStatus,
        _steps,
        _isTaskDone
    ) { state, task, shortStatus, steps, _isTaskDone ->
        state.copy(
            task = task,
            shortStatus = shortStatus,
            steps = steps,
            isTaskDone = _isTaskDone
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SingleTaskState())

    fun onEvent(event: SingleTaskEvent) {
        when (event) {
            is StepDone -> {
                viewModelScope.launch {
                    val step = event.step.copy(completedDate = Calendar.getInstance().timeInMillis)
                    repository.updateStepComplete(step)
                }
            }

            is TaskDone -> {
                viewModelScope.launch {
                    val taskComplete = TaskCompletedDate(
                        date = Calendar.getInstance().timeInMillis,
                        taskId = event.task.id
                    )
                    repository.addTaskComplete(taskComplete)
                }
                _state.update { it.copy(showDialog = true) }
            }
        }
    }
}
