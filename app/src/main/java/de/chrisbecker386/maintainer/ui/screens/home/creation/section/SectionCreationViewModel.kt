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

package de.chrisbecker386.maintainer.ui.screens.home.creation.section

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.chrisbecker386.maintainer.data.entity.Section
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
class SectionCreationViewModel @Inject constructor(
    private val repository: MaintainerRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _id = savedStateHandle.get<Int>("id")

    private val _title = MutableStateFlow<String?>(null)
    private val _imageRes = MutableStateFlow<Int?>(null)
    private val _sectionEditDataDefault = MutableStateFlow(SectionCreationData(id = _id))

    private val _sectionEditData = combine(
        _sectionEditDataDefault,
        _title,
        _imageRes
    ) { default, title, imageRes ->
        default.copy(title = title, imageRes = imageRes)
    }

    private val _error = MutableStateFlow<Throwable?>(null)
    private val _isLoading = MutableStateFlow(true)
    private val _isFinished = MutableStateFlow(false)

    val sectionEditState: StateFlow<DataResourceState<SectionCreationData>> = combine(
        _sectionEditData,
        _error,
        _isLoading,
        _isFinished
    ) { sectionEditData, error, isLoading, isFinished ->
        when {
            isLoading -> DataResourceState.Loading(sectionEditData, null)
            error != null -> DataResourceState.Error(sectionEditData, error.message ?: "", error)
            isFinished -> DataResourceState.Finished(
                sectionEditData,
                "Finished",
                "Section was successful created"
            )

            else -> DataResourceState.Success(sectionEditData)
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        DataResourceState.Loading(SectionCreationData(id = _id), null)
    )

    init {
        if ((_id != null) && (_id > 0)) {
            fetchSectionEditData(_id)
        } else {
            viewModelScope.launch { _isLoading.emit(false) }
        }
    }

    fun onEvent(event: SectionCreationEvent) {
        when (event) {
            is SectionCreationEvent.UpsertSection -> upsertSection(event)
            is SectionCreationEvent.AcceptError -> acceptError()
        }
    }

    private fun fetchSectionEditData(sectionId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            async {
                with(repository.getSection(sectionId)) {
                    _title.emit(this.title)
                    _imageRes.emit(this.imageRes)
                }
            }.await()
            async { _isLoading.emit(false) }.await()
        }
    }

    private fun upsertSection(event: SectionCreationEvent.UpsertSection) {
        viewModelScope.launch { _isLoading.emit(true) }
        viewModelScope.launch(Dispatchers.IO) {
            delay(350)
            async { upsertSectionInDB(event.section) }.await()
                .onSuccess {
                    _isLoading.emit(false)
                    _isFinished.emit(true)
                }.onFailure {
                    _error.emit(it)
                }
        }
    }

    private fun acceptError() = viewModelScope.launch { _error.emit(null) }

    private suspend fun upsertSectionInDB(section: Section) = if (section.id > 0) {
        runCatching { repository.updateSection(section) }
    } else {
        runCatching { repository.addSection(section) }
    }
}
