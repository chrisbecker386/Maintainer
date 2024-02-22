/*
 * Created by Christopher Becker on 12/05/2023, 08:35
 * Copyright (c) 2023. All rights reserved.
 * Last modified 12/05/2023, 08:35
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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import de.chrisbecker386.maintainer.ui.component.OverviewGrid
import de.chrisbecker386.maintainer.ui.component.ShortStatus
import de.chrisbecker386.maintainer.ui.theme.DIM_XS
import de.chrisbecker386.maintainer.ui.theme.DIM_XXS

@Composable
fun SingleSectionScreen(
    sectionType: Int?,
    onMachineClick: (Int) -> Unit = {}
) {
    val viewModel = hiltViewModel<SingleSectionViewModel>()
    val state by viewModel.state.collectAsState()
    SingleSection(
        state = state,
        onMachineClick = onMachineClick
    )
}

@Composable
private fun SingleSection(
    state: SingleSectionState,
    onEvent: (SingleSectionState) -> Unit = {},
    onMachineClick: (Int) -> Unit = {}
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(start = DIM_XS, end = DIM_XS, top = DIM_XS, bottom = DIM_XXS),
        verticalArrangement = Arrangement.spacedBy(DIM_XS)
    ) {
        ShortStatus(
            title = state.section.title,
            state = state.shortStatusState
        )
        OverviewGrid(
            items = state.machines.map { it.toGridItemData() },
            onItemClick = { onMachineClick(it) }
        )
    }
}
