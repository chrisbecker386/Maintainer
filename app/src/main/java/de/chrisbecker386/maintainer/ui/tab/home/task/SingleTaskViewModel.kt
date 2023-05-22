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
import de.chrisbecker386.maintainer.domain.repository.TaskRepository
import de.chrisbecker386.maintainer.ui.model.ShortStatusState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
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

    val state = combine(_state, _task, _shortStatus, _steps) { state, task, shortStatus, steps ->
        state.copy(
            task = task,
            shortStatus = shortStatus,
            steps = steps
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SingleTaskState())

    fun onEvent(event: SingleTaskEvent) {
        when (event) {
            is SingleTaskEvent.SetStepDone -> {
                viewModelScope.launch {
                    val newStep =
                        event.step.copy(completedDate = Calendar.getInstance().timeInMillis)
                    repository.updateStepComplete(newStep)
                }
            }
        }
    }
}
