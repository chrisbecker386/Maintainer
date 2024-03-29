/*
 * Created by Christopher Becker on 16/12/2023, 15:22
 * Copyright (c) 2023. All rights reserved.
 * Last modified 16/12/2023, 15:22
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

package de.chrisbecker386.maintainer.ui.screens.home.creation.machine

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.chrisbecker386.maintainer.data.entity.Machine
import de.chrisbecker386.maintainer.data.model.DataResourceState
import de.chrisbecker386.maintainer.domain.repository.MaintainerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MachineCreationViewModel @Inject constructor(
    private val repository: MaintainerRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _id = savedStateHandle.get<Int?>("machine_id")
    private val _foreignId = checkNotNull(savedStateHandle.get<Int>("machine_foreign_id"))

    private val _title = MutableStateFlow<String?>(null)
    private val _subtitle = MutableStateFlow<String?>(null)
    private val _imageRes = MutableStateFlow<Int?>(null)

    private val _machineEditDataDefault =
        MutableStateFlow(MachineEditData(id = _id, foreignId = _foreignId))
    private var _machineEditData = combine(
        _machineEditDataDefault,
        _title,
        _subtitle,
        _imageRes
    ) { machineDefault, title, subtitle, imageRes ->
        machineDefault.copy(title = title, subtitle = subtitle, imageRes = imageRes)
    }

    private val _error = MutableStateFlow<Throwable?>(null)
    private val _isLoading = MutableStateFlow(true)
    private val _isFinished = MutableStateFlow(false)

    val machineEditState: StateFlow<DataResourceState<MachineEditData>> = combine(
        _machineEditData,
        _error,
        _isLoading,
        _isFinished
    ) { machineData, error, isLaoding, isFinished ->
        when {
            isLaoding -> DataResourceState.Loading(machineData, null)
            error != null -> DataResourceState.Error(machineData, error.message ?: "", error)
            isFinished -> DataResourceState.Finished(
                machineData,
                "Finished",
                "Machine was successful created"
            )

            else -> DataResourceState.Success(machineData)
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        DataResourceState.Loading(MachineEditData(id = _id, foreignId = _foreignId), null)
    )

    init {
        if ((_id != null) && (_id > 0)) {
            fetchMachineEditData(_id)
        } else {
            viewModelScope.launch { _isLoading.emit(false) }
        }
    }

    fun onEvent(event: MachineEditEvent) {
        when (event) {
            is MachineEditEvent.UpsertMachine -> upsertMachine(event)
            is MachineEditEvent.AcceptError -> acceptError()
        }
    }

    private fun fetchMachineEditData(machineId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            async {
                with(repository.getMachine(machineId)) {
                    _title.emit(this.title)
                    _subtitle.emit(this.subtitle)
                    _imageRes.emit(this.imageRes)
                }
            }.await()
            async { _isLoading.emit(false) }.await()
        }
    }

    private fun upsertMachine(event: MachineEditEvent.UpsertMachine) {
        viewModelScope.launch { _isLoading.emit(true) }
        viewModelScope.launch(Dispatchers.IO) {
            delay(350)
            async { upsertMachineInDB(event.machine) }.await()
                .onSuccess {
                    _isLoading.emit(false)
                    _isFinished.emit(true)
                }.onFailure {
                    _error.emit(it)
                }
        }
    }

    private suspend fun upsertMachineInDB(machine: Machine) =
        if (machine.id > 0) {
            runCatching { repository.updateMachine(machine) }
        } else {
            runCatching { repository.addMachine(machine) }
        }

    private fun acceptError() = viewModelScope.launch { _error.emit(null) }
}
