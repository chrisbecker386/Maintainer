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
import de.chrisbecker386.maintainer.data.entity.Step
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

val dummyStepsDB = listOf(
    // clean water tank
    Step(
        id = 1,
        order = 1,
        title = "Open lid",
        imageRes = null,
        description = null,
        completedDate = null,
        taskId = 1
    ),
    Step(
        id = 2,
        order = 2,
        title = "Remove water tank",
        imageRes = null,
        description = "Carefully lift the tank out of the machine. So that no water is spilled unnecessarily",
        completedDate = null,
        taskId = 1
    ),
    Step(
        id = 3,
        order = 3,
        title = "Clean water tank",
        imageRes = null,
        description = "Clean the water tank properly with a brush, warm water and detergent. Then rinse again with clean water and dry the tank.",
        completedDate = null,
        taskId = 1
    ),
    Step(
        id = 4,
        order = 4,
        title = "Reinstall the water tank",
        imageRes = null,
        description = null,
        completedDate = null,
        taskId = 1
    ),
    Step(
        id = 5,
        order = 5,
        title = "Close lit",
        imageRes = null,
        description = null,
        completedDate = null,
        taskId = 1
    ),
    // unclogging
    Step(
        id = 6,
        order = 1,
        title = "Open lit",
        imageRes = null,
        description = null,
        completedDate = null,
        taskId = 2
    ),
    Step(
        id = 7,
        order = 2,
        title = "Empty Tank",
        imageRes = null,
        description = "Carefully empty the tank",
        completedDate = null,
        taskId = 2
    ),
    Step(
        id = 8,
        order = 3,
        title = "add descaling agent to the tank",
        imageRes = null,
        description = "Proportional to the size of the water tank: Dissolve the descaler in lukewarm water and then pour it into the water tank.",
        completedDate = null,
        taskId = 2
    ),
    Step(
        id = 9,
        order = 4,
        title = "uncoggling",
        imageRes = null,
        description = "Place a sufficiently large container under the coffee outlet. Let the machine warm up and then run the entire tank through once. If the container is full, stop briefly and empty the container. If the tank is empty, stop the machine. ",
        completedDate = null,
        taskId = 2
    ),
    Step(
        id = 10,
        order = 5,
        title = "Flush out residues",
        imageRes = null,
        description = "Fill the water tank again with ordinary water and let the tank run completely empty again. Carry out this procedure 2 times",
        completedDate = null,
        taskId = 2
    ),
    Step(
        id = 11,
        order = 6,
        title = "Refill tank",
        imageRes = null,
        description = null,
        completedDate = null,
        taskId = 2
    ),
    Step(
        id = 12,
        order = 7,
        title = "Close lit",
        imageRes = null,
        description = null,
        completedDate = null,
        taskId = 2
    )
)
