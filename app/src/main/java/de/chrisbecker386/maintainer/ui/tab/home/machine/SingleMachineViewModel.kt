/*
 * Created by Christopher Becker on 23/05/2023, 14:13
 * Copyright (c) 2023. All rights reserved.
 * Last modified 23/05/2023, 14:13
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

package de.chrisbecker386.maintainer.ui.tab.home.machine

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import de.chrisbecker386.maintainer.data.model.dummy.dummyStepsDB
import de.chrisbecker386.maintainer.data.model.dummy.dummyTasksDB
import de.chrisbecker386.maintainer.domain.repository.TaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SingleMachineViewModel @Inject constructor(private val repository: TaskRepository) :
    ViewModel() {

    // TODO only for dev has to removed soon!
    init {
        setDBEntries()
    }
    private fun setDBEntries() {
        CoroutineScope(IO).launch {
            withContext(Dispatchers.Default) {
                repository.upsertTasks(
                    dummyTasksDB
                )
            }
            withContext(Dispatchers.Default) {
                repository.insertSteps(
                    dummyStepsDB
                )
            }
        }
    }
}
