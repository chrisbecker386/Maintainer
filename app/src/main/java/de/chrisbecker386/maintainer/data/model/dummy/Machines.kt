/*
 * Created by Christopher Becker on 24/05/2023, 11:44
 * Copyright (c) 2023. All rights reserved.
 * Last modified 24/05/2023, 11:44
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
import de.chrisbecker386.maintainer.data.entity.Machine

val dummyMachineDB = listOf(
    Machine(
        id = 1,
        title = "espresso machine",
        subtitle = "best coffee",
        imageRes = R.drawable.coffee_maker_48px
    ),
    Machine(
        id = 2,
        title = "dish washer",
        subtitle = null,
        imageRes = R.drawable.dishwasher_gen_48px
    ),
    Machine(
        id = 3,
        title = "washing machine",
        subtitle = null,
        imageRes = R.drawable.local_laundry_service_48px
    )
)
