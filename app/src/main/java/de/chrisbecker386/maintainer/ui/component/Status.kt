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

package de.chrisbecker386.maintainer.ui.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Handyman
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import de.chrisbecker386.maintainer.R
import de.chrisbecker386.maintainer.data.model.MachineObject
import de.chrisbecker386.maintainer.data.model.TaskObject
import de.chrisbecker386.maintainer.data.model.dummy.devTasks
import de.chrisbecker386.maintainer.ui.model.ShortStatusState
import de.chrisbecker386.maintainer.ui.theme.DIM_L
import de.chrisbecker386.maintainer.ui.theme.DIM_NO
import de.chrisbecker386.maintainer.ui.theme.DIM_S
import de.chrisbecker386.maintainer.ui.theme.DIM_XS
import de.chrisbecker386.maintainer.ui.theme.DIM_XXS
import de.chrisbecker386.maintainer.ui.theme.DIM_XXXS
import de.chrisbecker386.maintainer.ui.theme.DIM_XXXXS
import de.chrisbecker386.maintainer.ui.theme.MaintainerTheme

@Composable
fun ShortStatus(
    modifier: Modifier = Modifier,
    title: String = "",
    state: ShortStatusState
) {
    Box(modifier = modifier) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(DIM_XS),
            elevation = DIM_NO,
            border = BorderStroke(width = DIM_XXXXS, color = MaterialTheme.colors.onBackground)
        ) {
            Column(
                Modifier.padding(
                    top = DIM_S,
                    bottom = DIM_S,
                    start = DIM_XS,
                    end = DIM_XS
                )
            ) {
                Row {
                    ConstraintLayout(Modifier.fillMaxWidth()) {
                        val (leftTextRef, rightIcon, rightText) = createRefs()
                        Text(
                            modifier = Modifier.constrainAs(leftTextRef) {
                                start.linkTo(parent.start)
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                            },
                            text = title,
                            style = MaterialTheme.typography.body1

                        )
                        Text(
                            modifier = Modifier.constrainAs(rightText) {
                                end.linkTo(parent.end)
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                            },
                            text = state.toString(),
                            style = MaterialTheme.typography.body1
                        )
                        Image(
                            modifier = Modifier
                                .constrainAs(rightIcon) {
                                    end.linkTo(rightText.start)
                                    top.linkTo(parent.top)
                                    bottom.linkTo(parent.bottom)
                                }
                                .padding(end = DIM_XXS),
                            imageVector = Icons.Default.Handyman,
                            contentDescription = "repair",
                            colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(DIM_XXS))
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(DIM_XXS),
                    progress = state.getProgress(),
                    color = MaterialTheme.colors.primary,
                    strokeCap = StrokeCap.Round
                )
            }
        }
    }
}

@Composable
fun MachineStatus(
    modifier: Modifier = Modifier,
    data: MachineObject
) {
    Box(modifier = modifier) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = DIM_XXXS,
            shape = RoundedCornerShape(DIM_S)
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
                        .padding(DIM_XS)
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Tasks",
                            style = MaterialTheme.typography.h6
                        )

                        Column(Modifier.fillMaxWidth()) {
                            data.getMaintainStats().forEach { state ->
                                TaskStatusRow(title = state.first, tasks = state.second)
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
            ShortStatus(
                Modifier.padding(DIM_XS),
                title = "Maintain Status",
                state = ShortStatusState(
                    numerator = 2,
                    denominator = 7
                )
            )
            NextMaintains(
                machineTitle = "machine",
                tasks = devTasks
            )
        }
    }
}
