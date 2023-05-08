/*
 * Created by Christopher Becker on 14/04/2023, 11:35
 * Copyright (c) 2023. All rights reserved.
 * Last modified 14/04/2023, 11:35
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
import de.chrisbecker386.maintainer.data.model.StepObject

val dummySteps: List<StepObject> = listOf(
    StepObject(
        id = 1,
        orderNumber = 1,
        title = "Step 1",
        description = "This is step 1",
        graphic = null
    ),

    StepObject(
        id = 2,
        orderNumber = 2,
        title = "Step 2",
        description = "This is step 2",
        graphic = R.drawable.espresso_machine
    ),

    StepObject(
        id = 3,
        orderNumber = 3,
        title = "Step 3",
        description = null,
        graphic = null
    )
)

val cleanWaterTank: List<StepObject> = listOf(
    StepObject(
        id = 1,
        orderNumber = 1,
        title = "Open lid",
        description = null,
        graphic = null
    ),

    StepObject(
        id = 2,
        orderNumber = 2,
        title = "Remove water tank",
        description = "Carefully lift the tank out of the machine. So that no water is spilled unnecessarily",
        graphic = null
    ),

    StepObject(
        id = 3,
        orderNumber = 3,
        title = "Clean water tank",
        description = "Clean the water tank properly with a brush, warm water and detergent. Then rinse again with clean water and dry the tank.",
        graphic = null
    ),
    StepObject(
        id = 4,
        orderNumber = 4,
        title = "Reinstall the water tank",
        description = null,
        graphic = null
    ),
    StepObject(
        id = 5,
        orderNumber = 5,
        title = "Close lit",
        description = null,
        graphic = null
    )
)
