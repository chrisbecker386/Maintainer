package de.chrisbecker386.maintainer.ui.info

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun InfoScreen(onTabSelectHome: () -> Unit = {}, onTabSelectSettings: () -> Unit = {}) {
    Column(Modifier.fillMaxWidth()) {
        Text(text = "InfoScreen")
    }
}