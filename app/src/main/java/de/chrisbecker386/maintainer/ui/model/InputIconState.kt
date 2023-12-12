/*
 * Created by Christopher Becker on 11/12/2023, 11:25
 * Copyright (c) 2023. All rights reserved.
 * Last modified 11/12/2023, 11:25
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

package de.chrisbecker386.maintainer.ui.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

data class InputIconState(
    val text: String? = null,
    val color: Color? = null,
    @DrawableRes
    val imageRes: Int? = null
)
