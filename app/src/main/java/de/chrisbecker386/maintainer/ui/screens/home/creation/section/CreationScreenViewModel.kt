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
import de.chrisbecker386.maintainer.domain.repository.MaintainerRepository
import de.chrisbecker386.maintainer.ui.screens.home.creation.section.SectionCreationEvent.ImageChange
import de.chrisbecker386.maintainer.ui.screens.home.creation.section.SectionCreationEvent.SectionConfirm
import de.chrisbecker386.maintainer.ui.screens.home.creation.section.SectionCreationEvent.TitleChange
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

    private val _id = savedStateHandle.get<Int>("id")

    private val _state = MutableStateFlow(SectionCreationState(id = _id))
    private val _title = MutableStateFlow<String?>(null)
    private val _imageRes = MutableStateFlow<Int?>(null)

    private val _isCreationComplete = combine(_title, _imageRes) { title, imageRes ->
        (title?.trim() != null) && (imageRes != null)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    val state = combine(
        _state,
        _title,
        _imageRes,
        _isCreationComplete
    ) { state, title, imageRes, isCreationComplete ->
        state.copy(title = title, imageRes = imageRes, isCreationComplete = isCreationComplete)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        SectionCreationState()
    )

    fun onEvent(event: SectionCreationEvent) {
        when (event) {
            is TitleChange -> {
                _title.value = event.title
            }

            is ImageChange -> {
                _imageRes.value = event.imageRes
            }

            is SectionConfirm -> {
                CoroutineScope(Dispatchers.IO).launch {
                    repository.addSection(event.section)
                }
                _state.update { it.copy(isNavigateUp = true) }
            }
        }
    }
}
