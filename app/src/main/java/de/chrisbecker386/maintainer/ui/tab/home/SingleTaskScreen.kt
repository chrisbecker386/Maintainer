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

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import de.chrisbecker386.maintainer.data.model.dummy.DummyData
import de.chrisbecker386.maintainer.ui.component.ShortStatus
import de.chrisbecker386.maintainer.ui.component.StepWithDetails
import de.chrisbecker386.maintainer.ui.theme.DIM_XS
import de.chrisbecker386.maintainer.ui.theme.MaintainerTheme

@Composable
fun SingleTaskScreen(taskType: String? = "Unclogging") {
    val task = remember(taskType) { DummyData.getTaskObject(taskType) }
    var doneTasks by rememberSaveable { mutableStateOf(0) }

    LazyColumn(Modifier.fillMaxWidth()) {
        item {
            ShortStatus(
                modifier = Modifier.padding(start = DIM_XS, end = DIM_XS, top = DIM_XS),
                title = "${task.title} Status",
                numerator = doneTasks,
                denominator = task.list.size
            )
        }
        items(count = task.list.size) { index ->
            StepWithDetails(
                Modifier.clickable {
                    task.list[index].done = true
                    doneTasks = task.list.filter { it.done }.size
                }.padding(start = DIM_XS, end = DIM_XS, top = DIM_XS),
                data = task.list[index]
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewSingleTaskScreen() {
    MaintainerTheme {
        SingleTaskScreen()
    }
}
