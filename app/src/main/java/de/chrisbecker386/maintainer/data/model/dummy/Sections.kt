/*
 * Created by Christopher Becker on 19/04/2023, 16:38
 * Copyright (c) 2023. All rights reserved.
 * Last modified 19/04/2023, 16:38
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

package de.chrisbecker386.maintainer.data.model.dummy

import de.chrisbecker386.maintainer.R
import de.chrisbecker386.maintainer.data.entity.Section

val devSections = listOf(
    Section(
        id = 1,
        title = "Kitchen",
        imageRes = R.drawable.kitchen_48px
    ),
    Section(
        id = 2,
        title = "Garage",
        imageRes = R.drawable.garage_home_48px
    ),
    Section(
        id = 3,
        title = "Utility room",
        imageRes = R.drawable.utility_room_48px
    ),
    Section(
        id = 4,
        title = "Computer",
        imageRes = R.drawable.computer_48px
    )
)
