/*
 * Created by Christopher Becker on 20/02/2024, 12:18
 * Copyright (c) 2024. All rights reserved.
 * Last modified 20/02/2024, 12:18
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

import OrientationPreviews
import ThemePreviews
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import de.chrisbecker386.maintainer.ui.theme.DIM_XS
import de.chrisbecker386.maintainer.ui.theme.MaintainerTheme

@Composable
fun HeadlineBold(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.h2,
        color = MaterialTheme.colors.onBackground
    )
}

@Composable
fun HeadlineSlim(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.h3,
        color = MaterialTheme.colors.onBackground
    )
}

@Composable
fun HeadlineBoldMedium(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.h4,
        color = MaterialTheme.colors.onBackground
    )
}

@Composable
fun HeadlineSlimMedium(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.h5,
        color = MaterialTheme.colors.onBackground
    )
}

@Composable
fun BodyText(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.body1,
        color = MaterialTheme.colors.onBackground
    )
}

@Composable
fun BodyText2(text: String, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.body2,
        color = MaterialTheme.colors.onBackground
    )
}

@Composable
fun SubtitleText(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.subtitle1,
        color = MaterialTheme.colors.onBackground
    )
}

@Composable
fun GridItemText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.subtitle1,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colors.onBackground,
        minLines = 2,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun StatusText(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.body1,
        color = MaterialTheme.colors.onBackground,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@ThemePreviews
@OrientationPreviews
@Composable
fun PreviewFormattedText() {
    MaintainerTheme {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(DIM_XS),
            verticalArrangement = Arrangement.spacedBy(
                DIM_XS
            )
        ) {
            HeadlineBold("HeadlineBold")
            HeadlineSlim("HeadlineSlim")
            HeadlineBoldMedium("HeadlineBoldMedium")
            HeadlineSlimMedium("HeadlineSlimMedium")
            BodyText("BodyText")
            BodyText2("BodyText2")
            SubtitleText("SubtitleText")
        }
    }
}
