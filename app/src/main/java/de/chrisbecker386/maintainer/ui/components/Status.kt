/*
 * Created by Christopher Becker on 20/04/2023, 09:24
 * Copyright (c) 2023. All rights reserved.
 * Last modified 20/04/2023, 09:24
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

package de.chrisbecker386.maintainer.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.chrisbecker386.maintainer.R
import de.chrisbecker386.maintainer.data.model.MaintainObject
import de.chrisbecker386.maintainer.data.model.TaskObject
import de.chrisbecker386.maintainer.data.model.dummy.dummyMaintains
import de.chrisbecker386.maintainer.data.model.getMaintainStats
import de.chrisbecker386.maintainer.ui.theme.DIM_L
import de.chrisbecker386.maintainer.ui.theme.DIM_S
import de.chrisbecker386.maintainer.ui.theme.DIM_XS
import de.chrisbecker386.maintainer.ui.theme.DIM_XXXS
import de.chrisbecker386.maintainer.ui.theme.MaintainerTheme


//TODO status Box that shows
// number maintainable and maintained Objects / tasks
// shows when and was is the next task/Object to care about
//@Composable
//fun CareObjectStatus(
//    modifier: Modifier = Modifier,
//    data: CareObject
//) {
//    Box(modifier = modifier) {
//        Card(
//            modifier = Modifier.fillMaxWidth(),
//            elevation = DIM_XXXS,
//            shape = RoundedCornerShape(DIM_S)
//        ) {
//
//
//        }
//    }
//}

@Composable
fun MaintainObjectStatus(
    modifier: Modifier = Modifier,
    data: MaintainObject
) {
    Box(modifier = modifier) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = DIM_XXXS,
            shape = RoundedCornerShape(DIM_S),
            backgroundColor = MaterialTheme.colors.primaryVariant,

            ) {
            Column {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.primaryVariant)
                        .padding(DIM_XS),

                    text = data.title,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h5
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.background)
                        .padding(DIM_XS)
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Tasks",
                            style = MaterialTheme.typography.h6
                        )

                        Column(Modifier.fillMaxWidth()) {
                            data.getMaintainStats().forEach { state ->
                                TaskStatusRow(title = state.first, tasks = state.third)
                            }
                        }
                    }
                }
            }

        }
    }
}

@Composable
private fun TaskStatusRow(
    modifier: Modifier = Modifier,
    title: String,
    tasks: List<TaskObject>
) {
    Row(
        modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.body1
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(DIM_XS))
                Text(
                    text = tasks.size.toString(),
                    style = MaterialTheme.typography.h4
                )
                Spacer(modifier = Modifier.width(DIM_L))
                LazyRow(modifier = Modifier.weight(3f)) {
                    items(tasks.size) { index ->
                        val task = tasks[index]
                        val painterId = task.graphic ?: R.drawable.kitchen_48px
                        Spacer(modifier = Modifier.width(DIM_XS))
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .padding(DIM_XXXS)
                                .clip(CircleShape),
                            Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = painterId),
                                contentDescription = task.title,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color = MaterialTheme.colors.secondaryVariant)
                                    .padding(DIM_XXXS)
                                    .clip(CircleShape)
                            )
                        }
                    }
                }

            }

        }

    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewMaintainObjectStatus() {
    MaintainerTheme {
        Column {
            MaintainObjectStatus(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(DIM_XS),
                data = dummyMaintains[0]
            )
        }
    }
}