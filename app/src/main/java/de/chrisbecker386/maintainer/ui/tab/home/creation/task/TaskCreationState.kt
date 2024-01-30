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

import de.chrisbecker386.maintainer.data.entity.Step

data class TaskCreationState(
    val id: Int? = null,
    val foreignId: Int? = null,
    val titles: Pair<String?, String?> = Pair(null, null),
    val imageRes: Int? = null,
    val repeatFrequencyAndTact: Pair<Long?, Int> = Pair(null, 1),
    val startDateTime: Long? = null,
    val steps: List<Step> = emptyList(),
    val isNavigateUp: Boolean = false,
    val isCreationComplete: Boolean = false
)
