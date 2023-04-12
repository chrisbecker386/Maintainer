package de.chrisbecker386.maintainer.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(onTabSelectInfo: () -> Unit = {}, onTabSelectSettings: () -> Unit = {}) {
    Column(Modifier.fillMaxWidth()) {
        Text(text = "HomeScreen")
    }
}