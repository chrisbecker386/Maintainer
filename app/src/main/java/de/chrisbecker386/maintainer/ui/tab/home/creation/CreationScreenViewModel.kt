/*
 * Created by Christopher Becker on 01/12/2023, 13:43
 * Copyright (c) 2023. All rights reserved.
 * Last modified 01/12/2023, 13:43
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

package de.chrisbecker386.maintainer.ui.tab.home.creation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.chrisbecker386.maintainer.data.entity.Section
import de.chrisbecker386.maintainer.domain.repository.MaintainerRepository
import de.chrisbecker386.maintainer.navigation.CreationType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreationScreenViewModel @Inject constructor(
    private val repository: MaintainerRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _creationType = checkNotNull(savedStateHandle.get<CreationType>("creation_type"))
    private val _id = savedStateHandle.get<Int>("id_type")
    private val _state = MutableStateFlow(CreationState(id = _id, type = _creationType))
    private val _title = MutableStateFlow<String?>(null)
    private val _imageRes = MutableStateFlow<Int?>(null)
    private val _isSectionDone =
        combine(_title, _imageRes) { title, imageRes ->
            _creationType == CreationType.Section &&
                !title.isNullOrEmpty() &&
                imageRes != null
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    private val _isCreationDone = combine(_isSectionDone) { section ->
        _isSectionDone.value
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    val state = combine(
        _state,
        _title,
        _imageRes,
        _isCreationDone
    ) { state, title, imageRes, isCreationDone ->
        state.copy(
            title = title,
            imageRes = imageRes,
            isCreationDone = isCreationDone
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CreationState())

    fun onEvent(event: CreationEvent) {
        when (event) {
            is CreationEvent.TitleChange -> {
                _title.value = event.title
            }

            is CreationEvent.ImageChange -> {
                _imageRes.value = event.imageRes
            }

            is CreationEvent.SectionConfirm -> {
                CoroutineScope(Dispatchers.IO).launch {
                    repository.addSection(event.section)
                }
                _state.update { it.copy(isNavigateUp = true) }
            }
        }
    }
}
