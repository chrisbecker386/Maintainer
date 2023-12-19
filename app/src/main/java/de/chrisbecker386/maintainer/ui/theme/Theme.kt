/*
 * Created by Christopher Becker on 13/04/2023, 17:40
 * Copyright (c) 2023. All rights reserved.
 * Last modified 13/04/2023, 17:40
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

package de.chrisbecker386.maintainer.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val _darkColorPalette = darkColors(
    primary = BaseGreen,
    primaryVariant = MediumGreen,
    secondary = DarkBlue,
    secondaryVariant = BaseBlue,
    background = Color.Black,
    surface = AiryBlack,
    onPrimary = AiryWhite,
    onSecondary = AiryWhite,
    onBackground = AiryWhite,
    onSurface = AiryWhite,
    onError = LightGray
)

private val _lightColorPalette = lightColors(
    primary = BaseGreen,
    primaryVariant = MediumGreen,
    secondary = BaseBlue,
    secondaryVariant = LightBlue,
    background = AiryWhite,
    surface = AiryWhite,
    onPrimary = AiryWhite,
    onSecondary = AiryWhite,
    onBackground = AiryBlack,
    onSurface = AiryBlack,
    onError = LightGray
)

@Composable
fun MaintainerTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        _darkColorPalette
    } else {
        _lightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = typo,
        shapes = Shapes,
        content = content
    )
}
