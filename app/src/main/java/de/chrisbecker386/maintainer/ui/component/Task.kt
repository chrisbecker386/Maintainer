/*
 * Created by Christopher Becker on 26/04/2023, 20:23
 * Copyright (c) 2023. All rights reserved.
 * Last modified 26/04/2023, 20:23
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

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Ballot
import androidx.compose.material.icons.filled.FactCheck
import androidx.compose.material.icons.filled.Timer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import de.chrisbecker386.maintainer.data.model.ApproximateTime
import de.chrisbecker386.maintainer.ui.theme.DIM_M_PLUS
import de.chrisbecker386.maintainer.ui.theme.DIM_NO
import de.chrisbecker386.maintainer.ui.theme.DIM_S
import de.chrisbecker386.maintainer.ui.theme.DIM_XS
import de.chrisbecker386.maintainer.ui.theme.DIM_XXS
import de.chrisbecker386.maintainer.ui.theme.DIM_XXXXS
import de.chrisbecker386.maintainer.ui.theme.MaintainerTheme

@Composable
fun TaskContent(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    approximateTime: ApproximateTime,
    preconditions: Int,
    numberOfSteps: Int

) {
    Box(modifier = modifier) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(DIM_S),
            elevation = DIM_NO,
            border = BorderStroke(width = DIM_XXXXS, color = MaterialTheme.colors.onBackground)
        ) {
            Column(
                Modifier
                    .padding(horizontal = DIM_XS, vertical = DIM_XXS)
            ) {
                Text(
                    text = title,
                    color = MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.h2
                )
                Text(
                    text = subtitle,
                    color = MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.body1
                )

                RowWithIconText(icon = Icons.Default.Timer, text = approximateTime.approxAsString)
                RowWithIconText(
                    icon = Icons.Default.FactCheck,
                    text = "$preconditions preconditions"
                )
                RowWithIconText(icon = Icons.Default.Ballot, text = "$numberOfSteps steps")
                Spacer(modifier = Modifier.height(DIM_XXS))
            }
        }
    }
}

@Composable
fun RowWithIconText(
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    text: String = ""
) {
    Row(modifier = modifier.fillMaxWidth()) {
        icon?.let { icon ->
            Image(
                modifier = Modifier.size(DIM_M_PLUS),
                imageVector = icon,
                contentDescription = text,
                colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground)
            )
        } ?: Spacer(modifier = Modifier.width(DIM_M_PLUS))
        Spacer(Modifier.width(DIM_XS))
        Text(
            text = text,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onBackground
        )
    }
}

@Preview
@Composable
fun PreviewTaskContent() {
    MaintainerTheme {
        TaskContent(
            Modifier.fillMaxWidth(),
            title = "task_title",
            subtitle = "task_sub_title",
            approximateTime = ApproximateTime.MIN_5,
            preconditions = 2,
            numberOfSteps = 4
        )
    }
}
