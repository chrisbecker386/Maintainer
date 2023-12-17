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
import de.chrisbecker386.maintainer.ui.tab.home.creation.task.TaskCreationEvent.SubtitleChange
import de.chrisbecker386.maintainer.ui.tab.home.creation.task.TaskCreationEvent.TitleChange
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

    private val _title = MutableStateFlow<String?>(null)
    private val _subtitle = MutableStateFlow<String?>(null)
    private val _imageRes = MutableStateFlow<Int?>(null)
    private val _state = MutableStateFlow(TaskCreationState(id = _id, foreignId = _foreignId))

    private val _isCreationComplete =
        combine(_title, _imageRes) { title, imageRes ->
            (title?.trim() != null) && (imageRes != null)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    val state = combine(
        _state,
        _title,
        _subtitle,
        _imageRes,
        _isCreationComplete
    ) { state, title, subtitle, imageRes, isCreationComplete ->
        state.copy(
            title = title,
            subtitle = subtitle,
            imageRes = imageRes,
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
            is TitleChange -> { _subtitle.value = event.title }
            is SubtitleChange -> { _subtitle.value = event.subtitle }
        }
    }
}
