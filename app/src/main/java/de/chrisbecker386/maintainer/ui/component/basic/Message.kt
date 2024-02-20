/*
 * Created by Christopher Becker on 06/02/2024, 13:07
 * Copyright (c) 2024. All rights reserved.
 * Last modified 06/02/2024, 13:07
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

package de.chrisbecker386.maintainer.ui.component.basic

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import de.chrisbecker386.maintainer.ui.component.BaseButton
import de.chrisbecker386.maintainer.ui.theme.typo

@Composable
fun MessageFullScreen(
    title: String = "Error",
    message: String = "undefine Error",
    onClick: () -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, style = typo.h2, color = MaterialTheme.colors.onBackground)
            Text(text = message, style = typo.body1, color = MaterialTheme.colors.onBackground)
            BaseButton(text = "Ok", onClick = onClick)
        }
    }
}
