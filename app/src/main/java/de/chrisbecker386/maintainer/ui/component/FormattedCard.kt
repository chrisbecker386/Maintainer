/*
 * Created by Christopher Becker on 20/02/2024, 20:11
 * Copyright (c) 2024. All rights reserved.
 * Last modified 20/02/2024, 20:11
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

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import de.chrisbecker386.maintainer.ui.theme.DIM_NO
import de.chrisbecker386.maintainer.ui.theme.DIM_S
import de.chrisbecker386.maintainer.ui.theme.DIM_S_PLUS
import de.chrisbecker386.maintainer.ui.theme.DIM_XS
import de.chrisbecker386.maintainer.ui.theme.DIM_XXS
import de.chrisbecker386.maintainer.ui.theme.DIM_XXXXS
import de.chrisbecker386.maintainer.ui.theme.MaintainerTheme

@Composable
fun EvenCard(
    modifier: Modifier = Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    content: @Composable ColumnScope.() -> Unit
) {
    MaintainerCard(
        modifier = modifier,
        horizontalAlignment = horizontalAlignment,
        content = content
    )
}

@Composable
fun UnevenCard(
    modifier: Modifier = Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    content: @Composable ColumnScope.() -> Unit
) {
    MaintainerCard(
        modifier = modifier,
        horizontalAlignment = horizontalAlignment,
        shape = RoundedCornerShape(DIM_S, DIM_S, DIM_S_PLUS, DIM_S_PLUS),
        content = content
    )
}

@Composable
fun StepCard(
    modifier: Modifier = Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    content: @Composable ColumnScope.() -> Unit
) {
    MaintainerCard(
        modifier = modifier,
        shape = RoundedCornerShape(
            topEnd = DIM_S,
            bottomStart = DIM_S,
            bottomEnd = DIM_S
        ),
        horizontalAlignment = horizontalAlignment,
        content = content
    )
}

@Composable
private fun MaintainerCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(DIM_S),
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = shape,
        elevation = DIM_NO,
        border = BorderStroke(
            width = DIM_XXXXS,
            color = MaterialTheme.colors.onBackground
        ),
        backgroundColor = MaterialTheme.colors.background
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(DIM_XS),
            horizontalAlignment = horizontalAlignment,
            content = content
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = false)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = false)
@Composable
fun PreviewCards() {
    MaintainerTheme {
        Column {
            EvenCard {
                HeadlineBold("DialogCard")
                Spacer(modifier = Modifier.size(DIM_S))
                BodyText("DialogCard")
            }
            Spacer(modifier = Modifier.size(DIM_S))
            UnevenCard {
                HeadlineBold("BaseCard")
                Spacer(modifier = Modifier.size(DIM_XXS))
                BodyText("BaseCard")
            }
        }
    }
}
