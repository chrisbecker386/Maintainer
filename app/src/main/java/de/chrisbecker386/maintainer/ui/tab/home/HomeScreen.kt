/*
 * Created by Christopher Becker on 10/05/2023, 12:59
 * Copyright (c) 2023. All rights reserved.
 * Last modified 10/05/2023, 12:59
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

package de.chrisbecker386.maintainer.ui.tab.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import de.chrisbecker386.maintainer.BuildConfig
import de.chrisbecker386.maintainer.data.model.GridItemData
import de.chrisbecker386.maintainer.data.model.dummy.DummyData
import de.chrisbecker386.maintainer.data.model.dummy.dummyCares
import de.chrisbecker386.maintainer.data.model.toCareGridItem
import de.chrisbecker386.maintainer.ui.component.NextMaintains
import de.chrisbecker386.maintainer.ui.component.OverviewGrid
import de.chrisbecker386.maintainer.ui.component.ShortStatus
import de.chrisbecker386.maintainer.ui.theme.DIM_XS
import de.chrisbecker386.maintainer.viewmodel.home.HomeScreenViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onCareObjectClick: (String) -> Unit = {},
    onMachineClick: (String) -> Unit = {}
) {
    val viewModel = hiltViewModel<HomeScreenViewModel>()

    if (BuildConfig.DEBUG) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(text = "HomeScreen", Modifier.align(Alignment.BottomEnd))
        }
    }
    Box(modifier = modifier) {
        LazyColumn(Modifier.fillMaxWidth()) {
            item {
                ShortStatus(
                    Modifier.padding(start = DIM_XS, end = DIM_XS, top = DIM_XS),
                    title = "Maintain Status",
                    numerator = 6,
                    denominator = 11
                )
            }
            item {
                NextMaintains(
                    Modifier
                        .padding(start = DIM_XS, end = DIM_XS, top = DIM_XS)
                        .clickable { onMachineClick(DummyData.cares[0].list[0].title) },
                    machineTitle = DummyData.cares[0].list[0].title,
                    tasks = DummyData.cares[0].list[0].list
                )
            }
            item {
                val list = mutableListOf<GridItemData>()
                dummyCares.forEach { list.add(it.toCareGridItem()) }
                OverviewGrid(
                    modifier = Modifier.padding(
                        start = DIM_XS,
                        end = DIM_XS,
                        top = DIM_XS
                    ),
                    items = list,
                    onItemClick = onCareObjectClick
                )
            }
        }
    }
}
