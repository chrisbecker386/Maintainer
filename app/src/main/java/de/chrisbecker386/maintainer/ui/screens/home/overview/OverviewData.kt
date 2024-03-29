/*
 * Created by Christopher Becker on 21/11/2023, 15:47
 * Copyright (c) 2023. All rights reserved.
 * Last modified 21/11/2023, 15:47
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

import de.chrisbecker386.maintainer.data.entity.Section
import de.chrisbecker386.maintainer.data.entity.relation.TaskWithDetails
import de.chrisbecker386.maintainer.ui.model.ShortStatusState

data class OverviewData(
    val shortStatus: ShortStatusState = ShortStatusState(0, 1),

    val nextTasks: List<TaskWithDetails> = emptyList(),
    val sections: List<Section> = emptyList()
)
