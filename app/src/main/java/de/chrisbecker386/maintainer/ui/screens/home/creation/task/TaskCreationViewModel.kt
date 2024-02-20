/*
 * Created by Christopher Becker on 16/12/2023, 15:24
 * Copyright (c) 2023. All rights reserved.
 * Last modified 16/12/2023, 15:24
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

package de.chrisbecker386.maintainer.ui.screens.home.creation.task

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.chrisbecker386.maintainer.data.entity.Step
import de.chrisbecker386.maintainer.data.entity.Task
import de.chrisbecker386.maintainer.data.model.DataResourceState
import de.chrisbecker386.maintainer.data.model.RepeatFrequency
import de.chrisbecker386.maintainer.domain.repository.MaintainerRepository
import de.chrisbecker386.maintainer.ui.theme.ICON_LIST
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskCreationViewModel @Inject constructor(
    private val repository: MaintainerRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _id = savedStateHandle.get<Int?>("task_id")
    private val _foreignId = checkNotNull(savedStateHandle.get<Int>("task_foreign_id"))

    // data
    private val _task = MutableStateFlow(
        Task(
            id = _id ?: 0,
            title = "",
            subtitle = null,
            imageRes = ICON_LIST.first(),
            repeatFrequency = RepeatFrequency.WEEKLY.value,
            tact = 1,
            machineId = _foreignId
        )
    )
    private var _steps = MutableStateFlow<List<Step>>(emptyList())
    private var _initStartDateTime = MutableStateFlow<Long?>(null)
    private val _taskEditDataDefault =
        MutableStateFlow(TaskEditData(id = _id, foreignId = _foreignId))

    private val _taskEditData = combine(
        _taskEditDataDefault,
        _task,
        _steps,
        _initStartDateTime
    ) { taskEditState, task, steps, initStartDateTime ->
        taskEditState.copy(task = task, steps = steps, startDateTime = initStartDateTime)
    }

    private val _error = MutableStateFlow<Throwable?>(null)
    private val _isLoading = MutableStateFlow(true)
    private val _isFinished = MutableStateFlow(false)

    val taskEditState: StateFlow<DataResourceState<TaskEditData>> = combine(
        _taskEditData,
        _error,
        _isLoading,
        _isFinished
    ) { taskEditData,
        error,
        isLoading,
        isFinished ->
        when {
            isLoading -> DataResourceState.Loading(taskEditData, null)
            error != null -> DataResourceState.Error(taskEditData, error.message ?: "", error)
            isFinished -> DataResourceState.Finished(
                taskEditData,
                "Finished",
                "Task was successful created"
            )

            else -> DataResourceState.Success(taskEditData)
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        DataResourceState.Loading(TaskEditData(id = _id, foreignId = _foreignId), null)
    )

    init {
        if ((_id != null) && (_id > 0)) {
            fetchTaskEditData(_id)
        } else {
            viewModelScope.launch { _isLoading.emit(false) }
        }
        Log.d("test", "value _id:$_id, _foreignId:$_foreignId")
    }

    fun onEvent(event: TaskCreationEvent) {
        when (event) {
            is TaskCreationEvent.AcceptError -> acceptError()
            is TaskCreationEvent.UpsertTask -> upsertTaskAndSteps(event)
        }
    }

    private fun fetchTaskEditData(taskId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            async {
                _task.emit(repository.getTask(taskId))
                _steps.emit(repository.getSteps(taskId))
                _initStartDateTime.emit(repository.getCompletedTask(taskId).first().date)
            }.await()

            async { _isLoading.emit(false) }.await()
        }
    }

    private fun acceptError() = viewModelScope.launch { _error.emit(null) }

    private fun upsertTaskAndSteps(event: TaskCreationEvent.UpsertTask) {
        viewModelScope.launch { _isLoading.emit(true) }

        viewModelScope.launch(Dispatchers.IO) {
            delay(350)
            // update task data
            async { updateForUpsertTask(event) }.await()

            // upsert task into db
            async { upsertTask(_task.value) }.await().onFailure {
                _error.emit(it)
                return@launch
            }

            if (_task.value.id == 0) {
                // get the latest task id
                async {
                    getLatestTaskId()
                        .onSuccess { id -> _task.update { it.copy(id = id) } }
                }.await().onFailure {
                    _error.emit(it)
                    return@launch
                }
            } else {
                // remove all existing steps if task already exists
                async {
                    removeStepsByTaskIdFromDb(_task.value.id)
                }.await().onFailure {
                    _error.emit(it)
                    return@launch
                }
            }

            // add all "new" steps to the db
            async {
                addSteps(_steps.value).onSuccess {
                    _isLoading.emit(false)
                    _isFinished.emit(true)
                }
            }.await().onFailure {
                _error.emit(it)
                return@launch
            }
        }
    }

    private fun updateForUpsertTask(event: TaskCreationEvent.UpsertTask) {
        with(event) {
            _task.update { task }
            _initStartDateTime.update { startDateTime }
            _steps.update { steps }
        }
    }

    private suspend fun upsertTask(task: Task) =
        if (task.id > 0) {
            runCatching { repository.updateTask(task) }
        } else {
            runCatching { repository.addTask(task) }
        }

    private suspend fun addSteps(steps: List<Step>) =
        runCatching { addStepsToDb(prepareStepsForDb(steps, _task.value.id)) }

    private suspend fun addStepsToDb(steps: List<Step>) = runCatching { repository.addSteps(steps) }
    private suspend fun removeStepsByTaskIdFromDb(taskId: Int) =
        runCatching { repository.removeStepsByTaskId(taskId) }

    private suspend fun getLatestTaskId(): Result<Int> = runCatching { repository.getLastTaskId() }

    private fun prepareStepsForDb(steps: List<Step>, taskId: Int): List<Step> {
        val localSteps = mutableListOf<Step>()
        steps.forEach {
            localSteps.add(
                Step(
                    order = it.order,
                    title = it.title,
                    description = it.description,
                    completedDate = it.completedDate,
                    taskId = taskId
                )
            )
        }
        return localSteps.toList()
    }
}
