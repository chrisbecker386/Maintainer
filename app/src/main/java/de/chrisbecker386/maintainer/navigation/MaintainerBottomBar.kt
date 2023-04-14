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

package de.chrisbecker386.maintainer.navigation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.primarySurface
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import de.chrisbecker386.maintainer.ui.theme.BOTTOM_BAR_HEIGHT
import de.chrisbecker386.maintainer.ui.theme.DIM_L
import de.chrisbecker386.maintainer.ui.theme.TAB_FADE_IN_DELAY_DURATION
import de.chrisbecker386.maintainer.ui.theme.TAB_FADE_IN_DURATION
import de.chrisbecker386.maintainer.ui.theme.TAB_FADE_OUT_DURATION
import de.chrisbecker386.maintainer.ui.theme.TAB_INACTIVE_OPACITY
import java.util.Locale

@Composable
fun MaintainerBottomBar(
    allTabScreens: List<Screen>,
    currentTab: Screen,
    onSelected: (Screen) -> Unit = {},
    modifier: Modifier
) {
    BottomNavigation(
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.primarySurface,
        contentColor = MaterialTheme.colors.onPrimary
    ) {
        MaintainerTabRow(
            allScreens = allTabScreens,
            currentScreen = currentTab,
            onTabSelected = onSelected
        )
    }
}

@Composable
fun MaintainerTabRow(
    allScreens: List<Screen>,
    currentScreen: Screen,
    onTabSelected: (Screen) -> Unit
) {
    Surface(
        Modifier
            .height(BOTTOM_BAR_HEIGHT)
            .fillMaxWidth()
    ) {
        Row(
            Modifier.selectableGroup(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            allScreens.forEach { screen ->
                MaintainerTab(
                    text = screen.title,
                    icon = screen.icon ?: Icons.Default.QuestionMark,
                    onSelected = { onTabSelected(screen) },
                    selected = currentScreen == screen
                )
            }
        }
    }
}

@Composable
private fun MaintainerTab(
    text: String,
    icon: ImageVector,
    onSelected: () -> Unit,
    selected: Boolean
) {
    val iconColor = MaterialTheme.colors.secondary
    val color = MaterialTheme.colors.onSurface
    val durationMillis = if (selected) TAB_FADE_IN_DURATION else TAB_FADE_OUT_DURATION
    val animSpec = remember {
        tween<Color>(
            durationMillis = durationMillis,
            easing = LinearEasing,
            delayMillis = TAB_FADE_IN_DELAY_DURATION
        )
    }
    val tabTintColor by animateColorAsState(
        targetValue = if (selected) color else color.copy(alpha = TAB_INACTIVE_OPACITY),
        animationSpec = animSpec
    )

    val iconTintColor by animateColorAsState(
        targetValue = if (selected) iconColor else color.copy(alpha = TAB_INACTIVE_OPACITY),
        animationSpec = animSpec
    )

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 4.dp, bottom = 4.dp)
            .selectable(
                selected = selected,
                onClick = onSelected,
                role = Role.Tab,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    bounded = false,
                    radius = Dp.Unspecified,
                    color = Color.Unspecified
                )
            )
            .animateContentSize()
            .height(BOTTOM_BAR_HEIGHT)
            .clearAndSetSemantics { contentDescription = text }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = iconTintColor,
            modifier = Modifier.size(DIM_L)
        )

        Text(
            text.lowercase(Locale.getDefault()),
            style = MaterialTheme.typography.body2,
            color = tabTintColor,
            textAlign = TextAlign.Center
        )
    }
}
