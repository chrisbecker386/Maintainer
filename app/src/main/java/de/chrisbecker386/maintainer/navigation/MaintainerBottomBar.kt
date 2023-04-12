package de.chrisbecker386.maintainer.navigation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.Settings
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
import de.chrisbecker386.maintainer.ui.theme.*
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
        contentColor = MaterialTheme.colors.onPrimary,
    ) {
        MaintainerTabRow(
            allScreens = allTabScreens,
            currentScreen = currentTab,
            onTabSelected = onSelected,
        )
    }
}

@Composable
fun MaintainerTabRow(
    allScreens: List<Screen>,
    currentScreen: Screen,
    onTabSelected: (Screen) -> Unit,
) {
    Surface(
        Modifier
            .height(BOTTOM_BAR_HEIGHT)
            .fillMaxWidth()
    ) {
        Row(
            Modifier.selectableGroup(), horizontalArrangement = Arrangement.SpaceAround
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

    Column(verticalArrangement = Arrangement.Center,
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
            .clearAndSetSemantics { contentDescription = text }) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = iconTintColor,
            modifier = Modifier.size(TAB_ICON_SIZE)
        )

        Text(
            text.lowercase(Locale.getDefault()),
            style = MaterialTheme.typography.body2,
            color = tabTintColor,
            textAlign = TextAlign.Center
        )
    }
}