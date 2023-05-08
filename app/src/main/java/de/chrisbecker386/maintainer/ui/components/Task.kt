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

package de.chrisbecker386.maintainer.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import de.chrisbecker386.maintainer.data.model.ApproximateTime
import de.chrisbecker386.maintainer.ui.theme.DIM_S
import de.chrisbecker386.maintainer.ui.theme.DIM_XS
import de.chrisbecker386.maintainer.ui.theme.DIM_XXS
import de.chrisbecker386.maintainer.ui.theme.MaintainerTheme

@Composable
fun TaskContent(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    approximateTime: ApproximateTime,
    numberOfSteps: Int

) {
    Box(modifier = modifier) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(DIM_S)),
            backgroundColor = MaterialTheme.colors.onError
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
                RowWithIconText(icon = Icons.Default.FactCheck, text = "Preconditions")
                RowWithIconText(icon = Icons.Default.Ballot, text = numberOfSteps.toString())
                Spacer(modifier = Modifier.height(DIM_XXS))
            }
        }
    }
}

@Composable
fun IconWithTextRow(modifier: Modifier) {
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewTaskContent() {
    MaintainerTheme {
        TaskContent(
            Modifier.fillMaxWidth(),
            title = "task_title",
            subtitle = "task_sub_title",
            approximateTime = ApproximateTime.MIN_5,
            numberOfSteps = 4
        )
    }
}
