/*
 * Created by Christopher Becker on 20/11/2023, 20:56
 * Copyright (c) 2023. All rights reserved.
 * Last modified 20/11/2023, 20:56
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

package de.chrisbecker386.maintainer.ui.screens.home.section

import android.icu.util.Calendar
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.chrisbecker386.maintainer.domain.repository.MaintainerRepository
import de.chrisbecker386.maintainer.ui.model.ShortStatusState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SingleSectionViewModel @Inject constructor(
    private val repository: MaintainerRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _section =
        repository.getSectionFlow(checkNotNull(savedStateHandle.get<Int>("section_type")))

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _machines = _section.flatMapLatest { section ->
        repository.getMachines(section.id)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _allSectionTasks = _section.flatMapLatest { section ->
        repository.getNumberOfAllTasksBySection(section.id)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0)

    private val _openSectionTasks = _section.flatMapLatest { section ->
        repository.getNumberOfAllOpenTasksBySection(section.id, getTime())
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0)

    private val _shortStatus = combine(_allSectionTasks, _openSectionTasks) { all, open ->
        ShortStatusState(all - open, all)
    }

    private val _state = MutableStateFlow(SingleSectionState())

    val state = combine(
        _state,
        _section,
        _shortStatus,
        _machines
    ) { state, section, shortStatusState, machines ->
        state.copy(
            section = section,
            shortStatusState = shortStatusState,
            machines = machines
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SingleSectionState())

    private fun getTime() = Calendar.getInstance().timeInMillis
}
