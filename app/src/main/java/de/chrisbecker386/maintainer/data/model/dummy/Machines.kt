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

val devMachines = listOf(
    Machine(
        id = 1,
        title = "espresso machine",
        subtitle = "best coffee",
        imageRes = R.drawable.coffee_maker_48px,
        section = 1
    ),
    Machine(
        id = 2,
        title = "Refrigerator",
        subtitle = null,
        imageRes = R.drawable.kitchen_48px,
        section = 1
    ),
    Machine(
        id = 3,
        title = "Washing Machine",
        subtitle = null,
        imageRes = R.drawable.dishwasher_gen_48px,
        section = 3
    ),
    Machine(
        id = 4,
        title = "Dryer",
        subtitle = null,
        imageRes = R.drawable.local_laundry_service_48px,
        section = 3
    ),
    Machine(
        id = 5,
        title = "Dishwasher",
        subtitle = null,
        imageRes = R.drawable.dishwasher_48px,
        section = 1
    ),
    Machine(
        id = 6,
        title = "Vacuum Cleaner",
        subtitle = null,
        imageRes = R.drawable.vacuum_48px,
        section = 3
    ),
    Machine(
        id = 7,
        title = "Car",
        subtitle = null,
        imageRes = R.drawable.directions_car_48px,
        section = 2
    ),
    Machine(
        id = 8,
        title = "Bicycle",
        subtitle = null,
        imageRes = R.drawable.pedal_bike_48px,
        section = 2
    ),
    Machine(
        id = 9,
        title = "Laptop",
        subtitle = null,
        imageRes = R.drawable.computer_48px,
        section = 4
    )
)
