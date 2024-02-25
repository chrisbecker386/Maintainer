/*
 * Created by Christopher Becker on 23/05/2023, 14:13
 * Copyright (c) 2023. All rights reserved.
 * Last modified 23/05/2023, 14:13
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

package de.chrisbecker386.maintainer.ui.screens.home.machine

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.chrisbecker386.maintainer.domain.repository.MaintainerRepository
import de.chrisbecker386.maintainer.ui.model.ShortStatusState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SingleMachineViewModel @Inject constructor(
    private val repository: MaintainerRepository,
    savedStateHandle: SavedStateHandle
) :
    ViewModel() {
    private val _machine =
        repository.getMachineFlow(checkNotNull(savedStateHandle.get<Int>("machine_type")))

    private val _tasks = _machine.flatMapLatest { machine ->
        repository.getTasksForMachineWithPreconditionsStepsCompletes(machine.id)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        emptyList()
    )

    private val _closedTasks = _tasks.mapLatest { tasks ->
        tasks.filter { task -> task.isValid() }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        emptyList()
    )

    private val _openTasks = _tasks.mapLatest { tasks ->
        tasks.filter { task -> !task.isValid() }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        emptyList()
    )

    private val _shortStatus = combine(_closedTasks, _tasks) { closedTask, tasks ->
        ShortStatusState(numerator = closedTask.size, denominator = tasks.size)
    }
    private val _state = MutableStateFlow(SingleMachineState())

    val state = combine(
        _state,
        _machine,
        _shortStatus,
        _openTasks,
        _closedTasks
    ) { state, machine, shortStatus, openTasks, closedTasks ->
        state.copy(
            machine = machine,
            shortStatus = shortStatus,
            openTasks = openTasks,
            closedTasks = closedTasks
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SingleMachineState())
}
