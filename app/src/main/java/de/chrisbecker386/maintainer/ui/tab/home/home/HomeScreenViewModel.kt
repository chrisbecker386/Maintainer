/*
 * Created by Christopher Becker on 24/05/2023, 13:03
 * Copyright (c) 2023. All rights reserved.
 * Last modified 24/05/2023, 13:03
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

package de.chrisbecker386.maintainer.ui.tab.home.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.chrisbecker386.maintainer.data.model.dummy.devMachines
import de.chrisbecker386.maintainer.data.model.dummy.devSections
import de.chrisbecker386.maintainer.data.model.dummy.devSteps
import de.chrisbecker386.maintainer.data.model.dummy.devTasks
import de.chrisbecker386.maintainer.domain.repository.MaintainerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: MaintainerRepository
) :
    ViewModel() {

    // TODO only for dev! has to removed as soon as item creation is added!
    init {
        setDBEntries()
    }

    private fun setDBEntries() {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Default) { repository.addSections(devSections) }
            withContext(Dispatchers.Default) { repository.addMachines(devMachines) }
            withContext(Dispatchers.Default) { repository.addTasks(devTasks) }
            withContext(Dispatchers.Default) { repository.addSteps(devSteps) }
        }
    }

    // TODO to here

    private val _sections = repository.getAllSections()

    private val _nextTasks = repository.getNextOpenTasks()

    private val _nextMachine = _nextTasks.flatMapLatest { task ->
        repository.getMachine(task.first().machineId)
    }

    private val _state = MutableStateFlow(HomeLandingState())

    val state = combine(
        _state,
        _nextMachine,
        _nextTasks,
        _sections
    ) { state, nextMachine, nextTasks, sections ->
        state.copy(
            nextMachine = nextMachine,
            nextTasks = nextTasks,
            sections = sections
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HomeLandingState())
}
