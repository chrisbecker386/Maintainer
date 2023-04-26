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
import de.chrisbecker386.maintainer.data.model.CareObject

val dummyCares = listOf(
    CareObject(
        id = 1,
        title = "kitchen",
        graphic = R.drawable.kitchen_48px,
        list = listOf(dummyMaintains[0], dummyMaintains[1])
    ),
    CareObject(
        id = 2,
        title = "car",
        graphic = R.drawable.directions_car_48px,
        list = listOf(dummyMaintains[2], dummyMaintains[3])
    )
)
