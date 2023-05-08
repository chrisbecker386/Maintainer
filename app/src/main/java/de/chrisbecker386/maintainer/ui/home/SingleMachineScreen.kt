/*
 * Created by Christopher Becker on 25/04/2023, 12:33
 * Copyright (c) 2023. All rights reserved.
 * Last modified 25/04/2023, 12:33
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

package de.chrisbecker386.maintainer.ui.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import de.chrisbecker386.maintainer.data.model.ApproximateTime
import de.chrisbecker386.maintainer.data.model.dummy.DummyData
import de.chrisbecker386.maintainer.ui.components.ShortStatus
import de.chrisbecker386.maintainer.ui.components.TaskContent
import de.chrisbecker386.maintainer.ui.theme.DIM_XS
import de.chrisbecker386.maintainer.ui.theme.MaintainerTheme

@Composable
fun SingleMachineScreen(
    machineType: String? = "Espresso Machine"
) {
    val machine = remember(machineType) { DummyData.getMaintainObject(machineType) }
    LazyColumn(Modifier.fillMaxWidth()) {
        item {
            ShortStatus(
                modifier = Modifier.padding(start = DIM_XS, end = DIM_XS, top = DIM_XS),
                title = "${machine.title} Status",
                numerator = 0,
                denominator = 2
            )
        }
        items(count = machine.list.size) { index ->
            TaskContent(
                modifier = Modifier.padding(start = DIM_XS, end = DIM_XS, top = DIM_XS),
                title = machine.list[index].title,
                subtitle = "none",
                approximateTime = ApproximateTime.MIN_45,
                numberOfSteps = 4

            )
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewSingleMachine() {
    MaintainerTheme {
        SingleMachineScreen()
    }
}
