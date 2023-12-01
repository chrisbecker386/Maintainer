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

import de.chrisbecker386.maintainer.data.entity.Precondition
import de.chrisbecker386.maintainer.data.entity.Step
import de.chrisbecker386.maintainer.navigation.CreationType

data class CreationState(
    val type: CreationType? = null,
    val id: Int? = null,
    val title: String? = null,
    val imageRes: Int? = null,
    val foreignId: Int? = null,
    val subtitle: String? = null,
    val duration: Int? = null,
    val repeatFrequency: Long? = null,
    val tact: Long? = null,
    val steps: List<Step> = emptyList(),
    val preconditions: List<Precondition> = emptyList(),
    val isNavigateUp: Boolean = false,
    val isCreationDone: Boolean = false
)
