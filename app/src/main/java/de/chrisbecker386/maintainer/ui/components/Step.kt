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

package de.chrisbecker386.maintainer.ui.components

import android.content.res.Configuration
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import de.chrisbecker386.maintainer.data.model.StepObject
import de.chrisbecker386.maintainer.data.model.dummy.dummySteps
import de.chrisbecker386.maintainer.ui.theme.ACCORDION_ANIMATION_DURATION
import de.chrisbecker386.maintainer.ui.theme.DIM_M
import de.chrisbecker386.maintainer.ui.theme.DIM_M_PLUS
import de.chrisbecker386.maintainer.ui.theme.DIM_XS
import de.chrisbecker386.maintainer.ui.theme.DIM_XXS
import de.chrisbecker386.maintainer.ui.theme.LIST_ITEM_TITLE_HEIGHT
import de.chrisbecker386.maintainer.ui.theme.MaintainerTheme

@Composable
fun Step(modifier: Modifier = Modifier, data: StepObject, showDetails: Boolean = false) {
    if (showDetails) {
        StepWithDetails(modifier = modifier, data = data)
    } else {
        StepNoDetails(modifier = modifier, data = data)
    }
}

@Composable
fun StepWithDetails(modifier: Modifier, data: StepObject) {
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
                    ConstraintLayout(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(LIST_ITEM_TITLE_HEIGHT)
                    ) {
                        val (imageRef, labelRef, iconRef) = createRefs()

                        data.graphic?.let { image ->
                            Image(
                                painter = painterResource(image),
                                contentScale = ContentScale.Crop,
                                contentDescription = data.title,
                                modifier = Modifier
                                    .constrainAs(imageRef) {
                                        start.linkTo(parent.start)
                                        top.linkTo(parent.top)
                                        bottom.linkTo(parent.bottom)
                                    }
                                    .size(LIST_ITEM_TITLE_HEIGHT)
                                    .clip(CircleShape)
                            )
                        } ?: Box(
                            modifier = Modifier
                                .constrainAs(imageRef) {
                                    start.linkTo(parent.start)
                                    top.linkTo(parent.top)
                                    bottom.linkTo(parent.bottom)
                                }
                                .size(LIST_ITEM_TITLE_HEIGHT)
                                .background(
                                    color = MaterialTheme.colors.primaryVariant,
                                    shape = RoundedCornerShape(DIM_M)
                                ),
                            Alignment.Center
                        ) {
                            Text(
                                text = data.orderNumber.toString(),
                                style = MaterialTheme.typography.subtitle1,
                                color = MaterialTheme.colors.background,
                                textAlign = TextAlign.Center
                            )
                        }
                        Text(
                            modifier = Modifier
                                .padding(start = DIM_XXS)
                                .constrainAs(labelRef) {
                                    start.linkTo(imageRef.end)
                                    top.linkTo(parent.top)
                                    bottom.linkTo(parent.bottom)
                                },
                            text = data.title,
                            style = MaterialTheme.typography.subtitle1,
                            color = MaterialTheme.colors.surface
                        )
                        data.description?.let {
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
                }
                data.description?.let {
                    if (expanded) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = DIM_XXS)
                        ) {
                            Spacer(modifier = Modifier.width(40.dp))
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

@Composable
private fun StepNoDetails(modifier: Modifier, data: StepObject) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(DIM_M_PLUS)
                .background(
                    color = MaterialTheme.colors.primaryVariant,
                    shape = RoundedCornerShape(DIM_M)
                ),
            Alignment.Center
        ) {
            Text(
                text = data.orderNumber.toString(),
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.background,
                textAlign = TextAlign.Center
            )
        }
        Text(
            modifier = Modifier.padding(start = DIM_XS),
            text = data.title,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onBackground
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewStep() {
    MaintainerTheme {
        Column {
            Step(data = dummySteps[0], showDetails = false)
            Step(data = dummySteps[1], showDetails = true)
            Step(data = dummySteps[2], showDetails = true)
        }
    }
}
