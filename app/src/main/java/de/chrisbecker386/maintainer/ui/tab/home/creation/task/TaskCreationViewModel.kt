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

package de.chrisbecker386.maintainer.ui.tab.home.creation.task

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.chrisbecker386.maintainer.domain.repository.MaintainerRepository
import de.chrisbecker386.maintainer.ui.tab.home.creation.task.TaskCreationEvent.TitlesChange
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TaskCreationViewModel @Inject constructor(
    private val repository: MaintainerRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // TODO this is only basic implementation should complete implemented

    private val _id = savedStateHandle.get<Int?>("task_id")
    private val _foreignId = checkNotNull(savedStateHandle.get<Int>("task_foreign_id"))

    // title and subtitle of task
    private val _titles = MutableStateFlow<Pair<String?, String?>>(Pair(null, null))

    // imageRes of the task
    private val _imageRes = MutableStateFlow<Int?>(null)

    // repeat and multiple times Frequency
    private val _repeatFrequencyAndTact = MutableStateFlow<Pair<Long?, Int>>(Pair(null, 1))

    private val _state = MutableStateFlow(TaskCreationState(id = _id, foreignId = _foreignId))

    private val _isCreationComplete =
        combine(_titles, _imageRes) { titles, imageRes ->
            (!titles.first?.trim().isNullOrBlank()) && (imageRes != null)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    val state = combine(
        _state,
        _titles,
        _imageRes,
        _repeatFrequencyAndTact,
        _isCreationComplete
    ) { state, titles, imageRes, repeatFrequencyAndTact, isCreationComplete ->
        state.copy(
            titles = titles,
            imageRes = imageRes,
            repeatFrequencyAndTact = repeatFrequencyAndTact,
            isCreationComplete = isCreationComplete
        )
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            TaskCreationState()
        )

    fun onEvent(event: TaskCreationEvent) {
        when (event) {
            is TitlesChange -> {
                _titles.value = event.titleAndSubTitle
            }
        }
    }
}
