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
import androidx.annotation.DrawableRes
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
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import de.chrisbecker386.maintainer.R
import de.chrisbecker386.maintainer.data.entity.Task
import de.chrisbecker386.maintainer.data.model.dummy.devMachines
import de.chrisbecker386.maintainer.data.model.dummy.devTasks
import de.chrisbecker386.maintainer.ui.theme.DIM_M
import de.chrisbecker386.maintainer.ui.theme.DIM_S
import de.chrisbecker386.maintainer.ui.theme.DIM_XL
import de.chrisbecker386.maintainer.ui.theme.DIM_XS
import de.chrisbecker386.maintainer.ui.theme.DIM_XXS
import de.chrisbecker386.maintainer.ui.theme.MaintainerTheme

@Composable
fun NextMaintains(
    machineTitle: String,
    tasks: List<Task>,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        UnevenCard {
            HeadlineBold(text = "next maintains")
            BodyText(text = machineTitle)
            Spacer(modifier = Modifier.height(DIM_S))
            tasks.forEachIndexed { index, task ->
                if (index < 2) {
                    NextMaintainItem(
                        graphic = task.imageRes,
                        title = task.title
                    )
                    if (index == 0 && tasks.size > 1) {
                        Spacer(modifier = Modifier.height(DIM_XS))
                    }
                }
            }
        }
    }
}

@Composable
private fun NextMaintainItem(
    @DrawableRes
    graphic: Int?,
    title: String,
    lastMaintained: String = "26.04.2023"
) {
    EvenCard {
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
                    id = graphic ?: R.drawable.block_48px
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
                BodyText2(text = lastMaintained)
                HeadlineSlim(text = title)
            }
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun PreviewNextMaintains() {
    MaintainerTheme { NextMaintains(devMachines.first().title, devTasks) }
}
