package de.chrisbecker386.maintainer.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SettingsScreen(onTabSelectHome: () -> Unit = {}, onTabSelectInfo: () -> Unit = {}) {
    Column(Modifier.fillMaxWidth()) {
        Text(text = "SettingsScreen")
    }
}
