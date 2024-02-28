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

package de.chrisbecker386.maintainer.ui.screens.home.task

import android.icu.util.Calendar
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.chrisbecker386.maintainer.data.entity.Task
import de.chrisbecker386.maintainer.data.entity.TaskCompletedDate
import de.chrisbecker386.maintainer.data.model.DataResourceState
import de.chrisbecker386.maintainer.data.model.RepeatFrequency
import de.chrisbecker386.maintainer.domain.repository.MaintainerRepository
import de.chrisbecker386.maintainer.ui.model.ShortStatusState
import de.chrisbecker386.maintainer.ui.screens.home.task.SingleTaskEvent.UpsertStep
import de.chrisbecker386.maintainer.ui.screens.home.task.SingleTaskEvent.UpsertTask
import de.chrisbecker386.maintainer.ui.theme.ICON_LIST
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingleTaskViewModel
@Inject
constructor(private val repository: MaintainerRepository, savedStateHandle: SavedStateHandle) :
    ViewModel() {
    private val _taskId = checkNotNull(savedStateHandle.get<Int>("task_type"))

    private val _task = MutableStateFlow(
        Task(
            id = _taskId,
            title = "",
            subtitle = null,
            imageRes = ICON_LIST.first(),
            repeatFrequency = RepeatFrequency.WEEKLY.value,
            tact = 1,
            machineId = 0
        )
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _steps = _task.flatMapLatest { task -> repository.getStepsFlow(task.id) }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        emptyList()
    )

    private val _shortStatus = combine(_steps, _task) { steps, task ->
        val numerator = steps.filter { it.isValid(task.getRepeatCycle()) }.size
        val denominator = steps.size
        ShortStatusState(numerator, denominator)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), ShortStatusState(0, 0))

    private val _singleTaskDataDefault = MutableStateFlow(SingleTaskData())

    private val _singleTaskData = combine(
        _singleTaskDataDefault,
        _task,
        _shortStatus,
        _steps
    ) { singleTaskData, task, shortStatus, steps ->
        singleTaskData.copy(
            task = task,
            shortStatus = shortStatus,
            steps = steps
        )
    }

    private val _error = MutableStateFlow<Throwable?>(null)
    private val _isLoading = MutableStateFlow(true)
    private val _isFinished = MutableStateFlow(false)

    val state: StateFlow<DataResourceState<SingleTaskData>> = combine(
        _singleTaskData,
        _error,
        _isLoading,
        _isFinished
    ) { singleTaskState,
        error,
        isLoading,
        isFinished ->
        when {
            isLoading -> DataResourceState.Loading(singleTaskState, null)
            error != null -> DataResourceState.Error(singleTaskState, error.message ?: "", error)
            isFinished -> DataResourceState.Finished(
                singleTaskState,
                "Finished",
                "Task was successful maintained"
            )

            else -> DataResourceState.Success(singleTaskState)
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        DataResourceState.Loading(SingleTaskData(task = _task.value, steps = _steps.value), null)
    )

    init {
        fetchSingleTaskData()
    }

    fun onEvent(event: SingleTaskEvent) {
        when (event) {
            SingleTaskEvent.AcceptError -> acceptError()
            is UpsertStep -> upsertStep(event)
            is UpsertTask -> upsertTask(event)
        }
    }

    private fun fetchSingleTaskData() {
        viewModelScope.launch(Dispatchers.IO) {
            async { _task.emit(repository.getTask(_taskId)) }.await()
            async { _isLoading.emit(false) }.await()
        }
    }

    private fun acceptError() = viewModelScope.launch { _error.emit(null) }

    private fun upsertStep(event: SingleTaskEvent.UpsertStep) {
        viewModelScope.launch(Dispatchers.IO) {
            async {
                val step = event.step.copy(completedDate = Calendar.getInstance().timeInMillis)
                repository.updateStep(step)
            }.await()
        }
    }

    private fun upsertTask(event: SingleTaskEvent.UpsertTask) {
        viewModelScope.launch(Dispatchers.IO) {
            val taskComplete = TaskCompletedDate(
                date = Calendar.getInstance().timeInMillis,
                taskId = _taskId
            )
            async {
                _isLoading.emit(true)
                delay(350)
            }.await()
            async { repository.addTaskComplete(taskComplete) }.await()
            async { _isLoading.emit(false) }.await()
            async { _isFinished.emit(true) }.await()
        }
    }
}
