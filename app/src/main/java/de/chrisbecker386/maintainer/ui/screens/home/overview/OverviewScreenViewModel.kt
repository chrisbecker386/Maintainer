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

package de.chrisbecker386.maintainer.ui.screens.home.overview

import android.icu.util.Calendar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.chrisbecker386.maintainer.data.entity.relation.TaskWithDetails
import de.chrisbecker386.maintainer.data.model.dummy.devMachines
import de.chrisbecker386.maintainer.data.model.dummy.devSections
import de.chrisbecker386.maintainer.data.model.dummy.devSteps
import de.chrisbecker386.maintainer.data.model.dummy.devTasks
import de.chrisbecker386.maintainer.domain.repository.MaintainerRepository
import de.chrisbecker386.maintainer.provider.interfaces.SharedPreferencesProvider
import de.chrisbecker386.maintainer.ui.model.ShortStatusState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class OverviewScreenViewModel @Inject constructor(
    private val sharePrefs: SharedPreferencesProvider,
    private val repository: MaintainerRepository
) :
    ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _numberOpenTasks = repository.getNumberOfOpenTasksFlow(getTime()).mapLatest { it }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _numberOfAllTasks = repository.getNumberOfAllTasks().mapLatest { it }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0)

    private val _shortStatus =
        combine(_numberOpenTasks, _numberOfAllTasks) { open, all ->
            ShortStatusState(all - open, all)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), ShortStatusState(0, 0))

    private var _openTasks = repository.getAllOpenTasksFlow(getTime())

    @OptIn(ExperimentalCoroutinesApi::class)
    private var _openTasksWithDetails: StateFlow<List<TaskWithDetails>> =
        _openTasks.flatMapLatest { tasks ->
            flow {
                val list = mutableListOf<TaskWithDetails>()
                tasks.forEach { task ->
                    repository.getTaskWithDetails(task.id)?.let { list.add(it) }
                }
                emit(list)
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private
    val _sections = repository.getAllSections()

    private val _overviewDataStateDefault = MutableStateFlow(OverviewData())

    val overviewData = combine(
        _overviewDataStateDefault,
        _shortStatus,
        _openTasksWithDetails,
        _sections
    ) { defaultState, shortStatus, openTasksWithDetails, sections ->
        defaultState.copy(
            shortStatus = shortStatus,
            nextTasks = openTasksWithDetails,
            sections = sections
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), OverviewData())

    private fun getTime() = Calendar.getInstance().timeInMillis

    init {
        setupDB()
    }

    private fun setupDB() {
        if (!sharePrefs.isAppConfigured()) {
            setDBEntries()
            sharePrefs.writeIsAppConfigured()
        }
    }

    private fun setDBEntries() {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Default) { repository.addSections(devSections) }
            withContext(Dispatchers.Default) { repository.addMachines(devMachines) }
            withContext(Dispatchers.Default) { repository.addTasks(devTasks) }
            withContext(Dispatchers.Default) { repository.addSteps(devSteps) }
        }
    }
}
