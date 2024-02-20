/*
 * Created by Christopher Becker on 21/02/2024, 20:07
 * Copyright (c) 2024. All rights reserved.
 * Last modified 21/02/2024, 20:07
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
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import de.chrisbecker386.maintainer.ui.theme.DIM_XXXXS
import de.chrisbecker386.maintainer.ui.theme.MaintainerTheme

@Composable
fun BaseButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    enable: Boolean = true
) {
    RoundedButton(
        modifier = modifier,
        title = text,
        textColor = MaterialTheme.colors.onBackground,
        backgroundColor = MaterialTheme.colors.background,
        borderStroke = BorderStroke(DIM_XXXXS, MaterialTheme.colors.onBackground),
        cornerPercent = 20,
        painterSource = null,
        enable = enable,
        onClick = onClick
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = false)
@Composable
fun PreviewFormattedButton() {
    MaintainerTheme {
        BaseButton(text = "BaseButton")
    }
}
