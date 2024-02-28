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

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Ballot
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import de.chrisbecker386.maintainer.ui.theme.DIM_M_PLUS
import de.chrisbecker386.maintainer.ui.theme.DIM_XS
import de.chrisbecker386.maintainer.ui.theme.DIM_XXS
import de.chrisbecker386.maintainer.ui.theme.MaintainerTheme

@Composable
fun TaskContent(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    numberOfSteps: Int
) {
    Box(modifier = modifier) {
        EvenCard {
            HeadlineBold(title)
            if (subtitle.isNotEmpty()) {
                BodyText(subtitle)
            }
            RowWithIconText(icon = Icons.Default.Ballot, text = "$numberOfSteps steps")
            Spacer(modifier = Modifier.height(DIM_XXS))
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
        BodyText(text)
    }
}

@Preview
@Composable
fun PreviewTaskContent() {
    MaintainerTheme {
        TaskContent(title = "task_title", subtitle = "", numberOfSteps = 4)
    }
}
