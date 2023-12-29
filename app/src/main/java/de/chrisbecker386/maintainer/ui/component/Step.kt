/*
 * Created by Christopher Becker on 14/04/2023, 11:26
 * Copyright (c) 2023. All rights reserved.
 * Last modified 14/04/2023, 11:26
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

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import de.chrisbecker386.maintainer.data.entity.Step
import de.chrisbecker386.maintainer.data.entity.Task
import de.chrisbecker386.maintainer.data.model.dummy.devSteps
import de.chrisbecker386.maintainer.data.model.dummy.devTasks
import de.chrisbecker386.maintainer.ui.theme.ACCORDION_ANIMATION_DURATION
import de.chrisbecker386.maintainer.ui.theme.DIM_L_PLUS
import de.chrisbecker386.maintainer.ui.theme.DIM_M
import de.chrisbecker386.maintainer.ui.theme.DIM_NO
import de.chrisbecker386.maintainer.ui.theme.DIM_S
import de.chrisbecker386.maintainer.ui.theme.DIM_XL
import de.chrisbecker386.maintainer.ui.theme.DIM_XS
import de.chrisbecker386.maintainer.ui.theme.DIM_XXS
import de.chrisbecker386.maintainer.ui.theme.DIM_XXXXS
import de.chrisbecker386.maintainer.ui.theme.MaintainerTheme

@Composable
fun StepWithDetails(modifier: Modifier = Modifier, step: Step, task: Task? = null) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        var expanded by rememberSaveable { mutableStateOf(false) }
        Box(modifier = Modifier) {
            Column(
                modifier = Modifier
                    .animateContentSize(
                        animationSpec = tween(durationMillis = ACCORDION_ANIMATION_DURATION)
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(Modifier.fillMaxWidth()) {
                    Row {
                        Box(
                            modifier = Modifier
                                .size(DIM_L_PLUS)
                                .background(
                                    color = MaterialTheme.colors.primaryVariant,
                                    shape = RoundedCornerShape(DIM_M)
                                ),
                            Alignment.Center
                        ) {
                            if (task != null && step.isValid(task.getRepeatCycle())) {
                                Image(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Check",
                                    colorFilter = ColorFilter.tint(color = MaterialTheme.colors.background)
                                )
                            } else {
                                Text(
                                    text = step.order.toString(),
                                    style = MaterialTheme.typography.subtitle1,
                                    color = MaterialTheme.colors.background
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(DIM_S))
                        Card(
                            Modifier
                                .padding(top = DIM_M)
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(
                                topEnd = DIM_S,
                                bottomStart = DIM_S,
                                bottomEnd = DIM_S
                            ),
                            elevation = DIM_NO,
                            border = BorderStroke(
                                width = DIM_XXXXS,
                                color = MaterialTheme.colors.onBackground
                            )
                        ) {
                            Column(Modifier) {
                                ConstraintLayout(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(DIM_L_PLUS)
                                ) {
                                    val (labelRef, iconRef) = createRefs()
                                    Text(
                                        modifier = Modifier
                                            .padding(start = DIM_XS)
                                            .constrainAs(labelRef) {
                                                start.linkTo(parent.start)
                                                top.linkTo(parent.top)
                                                bottom.linkTo(parent.bottom)
                                            },
                                        text = step.title,
                                        style = MaterialTheme.typography.subtitle1,
                                        color = MaterialTheme.colors.onBackground
                                    )
                                    step.description?.let {
                                        ExpandButton(
                                            modifier = Modifier.constrainAs(iconRef) {
                                                end.linkTo(parent.end)
                                                top.linkTo(parent.top)
                                                bottom.linkTo(parent.bottom)
                                            },
                                            expanded = expanded,
                                            onClick = { expanded = !expanded }
                                        )
                                    }
                                }
                                step.description?.let {
                                    if (expanded) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(
                                                    start = DIM_XS,
                                                    end = DIM_XL,
                                                    top = DIM_XXS,
                                                    bottom = DIM_XS
                                                )
                                        ) {
                                            Text(
                                                modifier = Modifier,
                                                text = it,
                                                style = MaterialTheme.typography.body2,
                                                color = MaterialTheme.colors.onBackground
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ExpandButton(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    onClick: () -> Unit
) {
    Box(modifier = modifier) {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                contentDescription = if (expanded) "collapse" else "expand",
                tint = MaterialTheme.colors.primary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStep() {
    MaintainerTheme {
        Column {
            StepWithDetails(step = devSteps[0], task = devTasks[0])
        }
    }
}
