/*
 * Created by Christopher Becker on 11/12/2023, 10:35
 * Copyright (c) 2023. All rights reserved.
 * Last modified 11/12/2023, 10:35
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
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.lerp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import de.chrisbecker386.maintainer.ui.model.InputIconState
import de.chrisbecker386.maintainer.ui.theme.BaseGray
import de.chrisbecker386.maintainer.ui.theme.DIM_L
import de.chrisbecker386.maintainer.ui.theme.DIM_M
import de.chrisbecker386.maintainer.ui.theme.DIM_S
import de.chrisbecker386.maintainer.ui.theme.DIM_XS
import de.chrisbecker386.maintainer.ui.theme.DIM_XXL
import de.chrisbecker386.maintainer.ui.theme.DIM_XXS
import de.chrisbecker386.maintainer.ui.theme.DIM_XXXL
import de.chrisbecker386.maintainer.ui.theme.DIM_XXXS
import de.chrisbecker386.maintainer.ui.theme.DIM_XXXXS
import de.chrisbecker386.maintainer.ui.theme.LightGray
import de.chrisbecker386.maintainer.ui.theme.MaintainerTheme

private val lineStroke = DIM_XXXXS
private val labelToTextSpace = DIM_XS
private val textToLineSpace = DIM_XXS
private val errorIconSpace = DIM_XXS

@OptIn(ExperimentalMotionApi::class)
@Composable
fun SingleLineTextInputField(
    modifier: Modifier = Modifier,
    value: String = "defaultValue",
    onValueChange: (String) -> Unit = {},
    enabled: Boolean = true,
    label: String = "label",
    sideIconState: InputIconState? = null,
    onSideIconClick: () -> Unit = {},
    subtextState: InputIconState? = null,
    lineColors: List<Color>? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    val textSelection = TextSelectionColors(colors.primary, BaseGray)
    CompositionLocalProvider(LocalTextSelectionColors provides textSelection) {
        val focusColor = lineColors?.first() ?: colors.primary
        val noFocusColor = lineColors?.last() ?: colors.onError

        val hasFocus = remember { mutableStateOf(false) }
        val lineColor = if (hasFocus.value) focusColor else noFocusColor

        val labelStyle = typography.h6.merge(TextStyle(color = colors.onBackground))
        val textStyle =
            typography.body1.merge(TextStyle(color = if (enabled) colors.onBackground else colors.onError))

        var inputText by remember { mutableStateOf(value) }
        Column(modifier = modifier.fillMaxWidth()) {
            ConstraintLayout(modifier = modifier.fillMaxWidth()) {
                val (textRef, sideIconRef) = createRefs()

                // TextLine
                BasicTextField(
                    value = inputText,
                    onValueChange = {
                        inputText = it
                        onValueChange(inputText)
                    },
                    modifier = Modifier
                        .constrainAs(textRef) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        }
                        .onFocusChanged { hasFocus.value = it.hasFocus }
                        .drawLine(lineColor, lineStroke),
                    enabled = enabled,
                    textStyle = textStyle,
                    keyboardOptions = keyboardOptions,
                    keyboardActions = keyboardActions,
                    singleLine = true,
                    maxLines = 1,
                    visualTransformation = VisualTransformation.None,
                    cursorBrush = SolidColor(colors.primary),
                    decorationBox = @Composable { innerTextField ->

                        val isCentered = (hasFocus.value.not() && value.isEmpty())
                        val labelSpace = with(LocalDensity.current) { labelStyle.lineHeight.toDp() }
                        val textSpace = with(LocalDensity.current) { textStyle.lineHeight.toDp() }
                        val height = labelSpace + labelToTextSpace + textSpace
                        val endSpace = DIM_L + lineStroke
                        val bottomSpace = textToLineSpace + lineStroke

                        val progress by animateFloatAsState(if (isCentered) 0f else 1f, label = "")

                        MotionLayout(
                            start = labelCentered,
                            end = labelAbove,
                            progress = progress,
                            modifier = Modifier
                                .height(height)
                                .padding(bottom = bottomSpace, end = endSpace)
                        ) {
                            Box(Modifier.layoutId(ConstrainIdLabel)) {
                                // this has to be in a box or else the lerp  animation doesn't work
                                Text(label, style = lerp(textStyle, labelStyle, progress))
                            }
                            Box(Modifier.layoutId(ConstrainIdText)) {
                                innerTextField()
                            }
                        }
                    }
                )

                // SideIcon
                sideIconState?.let { sideIconState ->
                    Box(
                        modifier = Modifier
                            .constrainAs(sideIconRef) {
                                end.linkTo(parent.end)
                                bottom.linkTo(textRef.bottom, margin = errorIconSpace)
                            }
                            .clickable { onSideIconClick() }
                    ) {
                        sideIconState.imageRes?.let { imageRes ->
                            Icon(
                                painter = painterResource(id = imageRes),
                                tint = sideIconState.color
                                    ?: LocalContentColor.current.copy(alpha = LocalContentAlpha.current),
                                contentDescription = sideIconState.text ?: "Icon"
                            )
                        }
                    }
                }
            }
            // Subtext
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(DIM_M)

            ) {
                subtextState?.imageRes?.let { res ->
                    Icon(
                        modifier = Modifier
                            .padding(top = DIM_XXXS)
                            .size(DIM_S),
                        painter = painterResource(id = res),
                        tint = subtextState.color ?: colors.onError,
                        contentDescription = subtextState.text
                    )
                }
                subtextState?.text?.let {
                    Text(
                        modifier = Modifier.padding(start = DIM_XXS, top = 3.dp),
                        text = it,
                        style = TextStyle(
                            fontSize = typography.subtitle2.fontSize,
                            lineHeight = typography.subtitle2.lineHeight,
                            fontFamily = typography.subtitle2.fontFamily,
                            fontWeight = typography.subtitle2.fontWeight,
                            textAlign = TextAlign.Center
                        ),
                        color = subtextState.color ?: BaseGray
                    )
                }
            }
        }
    }
}

@Composable
fun TextInputField(
    modifier: Modifier = Modifier,
    label: String = "",
    value: String = "",
    onValueChange: (String) -> Unit = {},
    enabled: Boolean = true,
    sideIcon: InputIconState? = null,
    onSideIconClick: () -> Unit = {},
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    val icon = when {
        enabled && sideIcon != null -> sideIcon
        !enabled && sideIcon != null -> InputIconState(
            text = sideIcon.text,
            color = sideIcon.color ?: LightGray,
            imageRes = sideIcon.imageRes
        )

        else -> null
    }

    val valueState: MutableState<String> = remember { mutableStateOf(value) }

    SingleLineTextInputField(
        modifier = modifier,
        value = valueState.value,
        onValueChange = {
            valueState.value = it
            onValueChange(it)
        },
        enabled = enabled,
        label = label,
        sideIconState = icon,
        onSideIconClick = onSideIconClick,
        subtextState = null,
        lineColors = null,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions
    )
}

@Composable
fun MultilineTextInputField(
    modifier: Modifier = Modifier,
    label: String? = null,
    text: String = "",
    maxChars: Int? = 30,
    onValueChange: (String) -> Unit = {},
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    val hasFocus = remember { mutableStateOf(false) }
    val textValue = remember { mutableStateOf(text) }
    val textCounter =
        remember { mutableStateOf("${textValue.value.length}/$maxChars") }

    val counterText: (Int) -> Unit = { textCounter.value = "$it/$maxChars " }

    val handleValueChange: (String) -> Unit = {
        textValue.value = it
        counterText(it.length)
        onValueChange(it)
    }

    Column(modifier = modifier) {
        if (label != null) {
            Text(
                modifier = Modifier.padding(bottom = DIM_XXXS),
                text = label,
                color = colors.onBackground,
                style = typography.h6
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(DIM_XXL + DIM_XXXL)
                .border(
                    width = DIM_XXXXS,
                    brush = if (hasFocus.value) SolidColor(colors.primary) else SolidColor(colors.onError),
                    shape = RectangleShape
                )
        ) {
            BasicTextField(
                modifier = Modifier
                    .padding(
                        top = DIM_XXS,
                        bottom = DIM_XXS,
                        start = DIM_XS,
                        end = DIM_XXS
                    )
                    .fillMaxSize(1f)
                    .onFocusChanged { hasFocus.value = it.hasFocus },
                value = textValue.value,
                textStyle = TextStyle(
                    color = colors.onBackground,
                    fontSize = typography.body1.fontSize,
                    lineHeight = typography.body1.lineHeight,
                    fontFamily = typography.body1.fontFamily,
                    fontWeight = typography.body1.fontWeight,
                    textAlign = TextAlign.Left
                ),
                cursorBrush = SolidColor(colors.primary),
                onValueChange = { handleValueChange(it) },
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions
            )
        }
        maxChars?.let {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = DIM_XXXS),
                contentAlignment = Alignment.TopEnd
            ) {
                Text(
                    text = textCounter.value,
                    color = if (textValue.value.length > it) colors.onError else colors.onBackground,
                    fontSize = typography.subtitle2.fontSize,
                    lineHeight = typography.subtitle2.lineHeight,
                    fontFamily = typography.subtitle2.fontFamily,
                    fontWeight = typography.subtitle2.fontWeight
                )
            }
        }
    }
}

private const val ConstrainIdText = "text"
private const val ConstrainIdLabel = "label"

private val labelCentered = ConstraintSet {
    val text = createRefFor(ConstrainIdText)
    val label = createRefFor(ConstrainIdLabel)
    constrain(text) {
        bottom.linkTo(parent.bottom)
        start.linkTo(parent.start)
    }
    constrain(label) {
        bottom.linkTo(parent.bottom)
        start.linkTo(parent.start)
    }
}

private val labelAbove = ConstraintSet {
    val text = createRefFor(ConstrainIdText)
    val label = createRefFor(ConstrainIdLabel)
    constrain(text) {
        bottom.linkTo(parent.bottom)
        start.linkTo(parent.start)
    }
    constrain(label) {
        top.linkTo(parent.top)
        start.linkTo(parent.start)
    }
}

private fun Modifier.drawLine(
    lineColor: Color,
    lineStroke: Dp
) = drawWithContent {
    drawContent()
    if (lineStroke == Dp.Hairline) return@drawWithContent
    val strokeWidth = lineStroke.value * density
    val y = size.height - strokeWidth / 2
    drawLine(
        lineColor,
        Offset(0f, y),
        Offset(size.width, y),
        strokeWidth
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
fun PreviewTextFields() {
    MaintainerTheme {
        Column(
            Modifier
                .fillMaxSize()
                .background(colors.background)
                .padding(start = DIM_XS, end = DIM_XS, top = DIM_XS),
            verticalArrangement = Arrangement.spacedBy(DIM_XS)
        ) {
            BodyText("Single line TF")
            SingleLineTextInputField(
                modifier = Modifier
                    .fillMaxWidth(),
                label = "TextField"
            )
            BodyText("TextField")
            TextInputField(
                modifier = Modifier
                    .fillMaxWidth(),
                label = "TextField"
            )
            BodyText("Multi line TF")
            MultilineTextInputField(
                modifier = Modifier
                    .fillMaxWidth(),
                label = "TextField",
                text = "some chars",
                maxChars = 30
            )
        }
    }
}
