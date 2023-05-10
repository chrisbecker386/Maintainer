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

import android.annotation.SuppressLint
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import de.chrisbecker386.maintainer.data.model.TaskObject
import de.chrisbecker386.maintainer.data.model.dummy.dummyMaintains
import de.chrisbecker386.maintainer.ui.theme.DIM_M
import de.chrisbecker386.maintainer.ui.theme.DIM_NO
import de.chrisbecker386.maintainer.ui.theme.DIM_S
import de.chrisbecker386.maintainer.ui.theme.DIM_S_PLUS
import de.chrisbecker386.maintainer.ui.theme.DIM_XL
import de.chrisbecker386.maintainer.ui.theme.DIM_XS
import de.chrisbecker386.maintainer.ui.theme.DIM_XXS
import de.chrisbecker386.maintainer.ui.theme.DIM_XXXXS
import de.chrisbecker386.maintainer.ui.theme.MaintainerTheme

@Composable
fun NextMaintains(
    modifier: Modifier = Modifier,
    machineTitle: String,
    tasks: List<TaskObject>
) {
    Box(modifier = modifier) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(
                topStart = DIM_S,
                topEnd = DIM_S,
                bottomStart = DIM_S_PLUS,
                bottomEnd = DIM_S_PLUS
            ),
            elevation = DIM_NO,
            border = BorderStroke(
                width = DIM_XXXXS,
                color = MaterialTheme.colors.onBackground
            )

        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(DIM_XS)
            ) {
                Text(
                    text = "next maintains",
                    style = MaterialTheme.typography.h2,
                    color = MaterialTheme.colors.onBackground
                )
                Text(
                    text = machineTitle,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onBackground
                )
                Spacer(modifier = Modifier.height(DIM_S))
                tasks.forEachIndexed { index, task ->
                    if (index < 2) {
                        NextMaintainItem(
                            graphic = task.graphic,
                            title = task.title,
                            isMaintained = index == 0
                        )
                        if (index == 0) {
                            Spacer(modifier = Modifier.height(DIM_XS))
                        }
                    }
                }
            }
        }
    }
}

@SuppressLint("PrivateResource")
@Composable
fun NextMaintainItem(
    modifier: Modifier = Modifier,
    @DrawableRes
    graphic: Int?,
    title: String,
    lastMaintained: String = "26.04.2023",
    isMaintained: Boolean
) {
    Box(modifier = modifier) {
        Card(
            modifier =
            Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(DIM_S),
            border = BorderStroke(
                width = DIM_XXXXS,
                color = MaterialTheme.colors.onBackground
            )
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = DIM_S, bottom = DIM_S, start = DIM_M),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(DIM_XL)
                        .clip(
                            RoundedCornerShape(DIM_XXS)
                        ),
                    painter = painterResource(
                        id = graphic
                            ?: com.google.android.material.R.drawable.mtrl_ic_checkbox_unchecked
                    ),
                    contentDescription = title,
                    contentScale = ContentScale.Crop,
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground)
                )
                Spacer(modifier = Modifier.width(DIM_XS))
                Column(
                    Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = lastMaintained,
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onBackground
                    )
                    Text(
                        text = title,
                        style = MaterialTheme.typography.h3,
                        color = MaterialTheme.colors.onBackground
                    )
                }
            }
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun PreviewNextMaintains() {
    MaintainerTheme {
        NextMaintains(
            Modifier
                .fillMaxWidth()
                .padding(DIM_XS),
            dummyMaintains[0].title,
            dummyMaintains[0].list
        )
    }
}
