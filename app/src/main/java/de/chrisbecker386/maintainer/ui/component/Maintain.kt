/*
 * Created by Christopher Becker on 26/04/2023, 09:44
 * Copyright (c) 2023. All rights reserved.
 * Last modified 26/04/2023, 09:44
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

package de.chrisbecker386.maintainer.ui.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat.getString
import de.chrisbecker386.maintainer.R
import de.chrisbecker386.maintainer.data.entity.TaskCompletedDate
import de.chrisbecker386.maintainer.data.entity.relation.TaskWithDetails
import de.chrisbecker386.maintainer.data.model.dummy.devMachines
import de.chrisbecker386.maintainer.data.model.dummy.devSections
import de.chrisbecker386.maintainer.data.model.dummy.devTasks
import de.chrisbecker386.maintainer.data.utility.toTimeIndicationOrFormat
import de.chrisbecker386.maintainer.ui.theme.DIM_S
import de.chrisbecker386.maintainer.ui.theme.DIM_XS
import de.chrisbecker386.maintainer.ui.theme.DIM_XXL
import de.chrisbecker386.maintainer.ui.theme.MaintainerTheme

@Composable
fun NextMaintains(
    tasksWithDetails: List<TaskWithDetails>,
    modifier: Modifier = Modifier,
    onTaskClick: (Int) -> Unit = {}
) {
    UnevenCard(modifier = modifier) {
        HeadlineBold(text = "next maintains")
        BodyText(text = stringResource(R.string.your_open_tasks))
        Spacer(modifier = Modifier.height(DIM_S))
        tasksWithDetails.forEachIndexed { index, item ->
            if (index < 2) {
                NextMaintainItem(item, onTaskClick)
                if (index == 0 && tasksWithDetails.size > 1) {
                    Spacer(modifier = Modifier.height(DIM_XS))
                }
            }
        }
    }
}

@Composable
private fun NextMaintainItem(
    item: TaskWithDetails,
    onClick: (Int) -> Unit = {}
) {
    EvenCard(Modifier.clickable(onClick = { onClick(item.task.id) })) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CaptionText(
                    text =
                    if (item.completedDates.isNotEmpty()) {
                        item.completedDates.last().date
                            .toTimeIndicationOrFormat(LocalContext.current)
                    } else {
                        getString(LocalContext.current, R.string.today)
                    }
                )
                Image(
                    modifier = Modifier.size(DIM_XXL),
                    painter = painterResource(
                        id = item.task.imageRes
                    ),
                    contentDescription = item.task.title,
                    contentScale = ContentScale.Crop,
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground)
                )
            }
            Spacer(modifier = Modifier.width(DIM_XS))
            Column(
                Modifier.weight(3f),
                verticalArrangement = Arrangement.Top
            ) {
                BodyText2(text = item.machine.title)
                HeadlineSlim(text = item.task.title)
            }
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun PreviewNextMaintains() {
    MaintainerTheme {
        NextMaintains(
            listOf(
                TaskWithDetails(
                    devTasks[0],
                    devMachines[0],
                    devSections[0],
                    listOf(
                        TaskCompletedDate(0, 1709059410989, devTasks[0].id)
                    )
                )
            )
        )
    }
}
