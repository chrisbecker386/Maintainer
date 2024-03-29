/*
 * Created by Christopher Becker on 14/12/2023, 14:12
 * Copyright (c) 2023. All rights reserved.
 * Last modified 14/12/2023, 14:12
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

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonElevation
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import de.chrisbecker386.maintainer.R
import de.chrisbecker386.maintainer.ui.theme.BaseBlue
import de.chrisbecker386.maintainer.ui.theme.BaseOrange
import de.chrisbecker386.maintainer.ui.theme.DIM_L
import de.chrisbecker386.maintainer.ui.theme.DIM_NO
import de.chrisbecker386.maintainer.ui.theme.DIM_S
import de.chrisbecker386.maintainer.ui.theme.DIM_XL
import de.chrisbecker386.maintainer.ui.theme.DIM_XS
import de.chrisbecker386.maintainer.ui.theme.DIM_XXXS
import de.chrisbecker386.maintainer.ui.theme.DIM_XXXXS
import de.chrisbecker386.maintainer.ui.theme.LightBlue
import de.chrisbecker386.maintainer.ui.theme.LightOrange
import de.chrisbecker386.maintainer.ui.theme.MaintainerTheme

@Composable
fun RoundedButton(
    modifier: Modifier = Modifier,
    title: String,
    textColor: Color = colors.onBackground,
    style: TextStyle = typography.body1,
    position: Arrangement.Horizontal = Arrangement.Center,
    backgroundColor: Color = colors.background,
    borderStroke: BorderStroke? = BorderStroke(width = DIM_XXXXS, color = colors.onBackground),
    cornerPercent: Int = 50,
    painterSource: Painter? = null,
    contentPadding: PaddingValues = PaddingValues(DIM_S, DIM_XS),
    enable: Boolean = true,
    onClick: () -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }

) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enable,
        elevation = null,
        shape = RoundedCornerShape(cornerPercent),
        border = borderStroke,
        colors = ButtonColorsImpl(
            backgroundColor = backgroundColor,
            contentColor = textColor,
            disabledBackgroundColor = colors.onError,
            disabledContentColor = textColor
        ),
        contentPadding = contentPadding,
        interactionSource = interactionSource
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = position // here
            ) {
                painterSource?.let {
                    Column(
                        modifier
                            .align(Alignment.CenterVertically)
                            .padding(end = DIM_XS)
                            .size(style.fontSize.value.dp)
                    ) {
                        Image(
                            painter = painterSource,
                            contentDescription = "$title-icon",
                            colorFilter = ColorFilter.tint(color = colors.onBackground),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                Text(title, style = style)
            }

            Box(
                modifier = Modifier
                    .height(DIM_XXXS)
                    .width(DIM_XL)
                    .clip(RoundedCornerShape(50))
                    .background(colors.primary)
            )
        }
    }
}

@Composable
fun SingleIconButton(
    modifier: Modifier = Modifier,
    size: Dp = DIM_L,
    @DrawableRes
    imageRes: Int = R.drawable.add_48px,
    contentDescription: String? = null,
    enabled: Boolean = true,
    onClick: () -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ButtonElevation? = null,
    colors: ButtonColors = ButtonColorsImpl(),
    shape: Shape = RoundedCornerShape(50),
    border: BorderStroke? = BorderStroke(
        width = DIM_XXXXS,
        color = MaterialTheme.colors.onBackground
    ),
    contentPadding: PaddingValues = PaddingValues(DIM_NO)
) {
    Button(
        onClick = onClick,
        modifier = modifier.size(size),
        enabled = enabled,
        interactionSource = interactionSource,
        elevation = elevation,
        shape = shape,
        border = border,
        colors = colors,
        contentPadding = contentPadding
    ) {
        Image(
            painterResource(id = imageRes),
            contentDescription = contentDescription,
            colorFilter = ColorFilter.tint(color = MaterialTheme.colors.onBackground)
        )
    }
}

class ButtonColorsImpl(
    private val backgroundColor: Color = BaseBlue,
    private val contentColor: Color = BaseOrange,
    private val disabledBackgroundColor: Color = LightBlue,
    private val disabledContentColor: Color = LightOrange
) : ButtonColors {
    @Composable
    override fun backgroundColor(enabled: Boolean): State<Color> {
        return rememberUpdatedState(if (enabled) backgroundColor else disabledBackgroundColor)
    }

    @Composable
    override fun contentColor(enabled: Boolean): State<Color> {
        return rememberUpdatedState(if (enabled) contentColor else disabledContentColor)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as ButtonColorsImpl

        if (backgroundColor != other.backgroundColor) return false
        if (contentColor != other.contentColor) return false
        if (disabledBackgroundColor != other.disabledBackgroundColor) return false
        return disabledContentColor == other.disabledContentColor
    }

    override fun hashCode(): Int {
        var result = backgroundColor.hashCode()
        result = 31 * result + contentColor.hashCode()
        result = 31 * result + disabledBackgroundColor.hashCode()
        result = 31 * result + disabledContentColor.hashCode()
        return result
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRoundedButton() {
    MaintainerTheme {
        Column {
            RoundedButton(
                painterSource = painterResource(R.drawable.computer_48px),
                title = "text",
                textColor = colors.onBackground,
                backgroundColor = colors.background,
                enable = true,
                contentPadding = PaddingValues(25.dp, 5.dp)
            )
            SingleIconButton(
                contentPadding = PaddingValues(0.dp),
                enabled = true
            )
        }
    }
}
