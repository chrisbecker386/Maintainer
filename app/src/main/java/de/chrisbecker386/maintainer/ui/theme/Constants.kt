/*
 * Created by Christopher Becker on 13/04/2023, 17:40
 * Copyright (c) 2023. All rights reserved.
 * Last modified 13/04/2023, 17:40
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

package de.chrisbecker386.maintainer.ui.theme

import androidx.compose.ui.unit.dp
import de.chrisbecker386.maintainer.R
import de.chrisbecker386.maintainer.data.entity.Step

// dimensions
/** 0.dp*/
val DIM_NO = 0.dp

/** 1.dp*/
val DIM_XXXXS = 1.dp

/** 2.dp*/
val DIM_XXXS = 2.dp

/** 3.dp*/
val DIM_XXXS_PLUS = 3.dp

/** 4.dp*/
val DIM_XXS = 4.dp

/** 6.dp*/
val DIM_XXS_PLUS = 6.dp

/** 8.dp*/
val DIM_XS = 8.dp

/** 12.dp*/
val DIM_S = 12.dp

/** 16.dp*/
val DIM_S_PLUS = 16.dp

/** 20.dp*/
val DIM_M = 20.dp

/** 24.dp*/
val DIM_M_PLUS = 24.dp

/** 32.dp*/
val DIM_L = 32.dp

/** 40.dp*/
val DIM_L_PLUS = 40.dp

/** 48.dp*/
val DIM_XL = 48.dp

/** 60.dp*/
val DIM_XXL = 60.dp

/** 72.dp*/
val DIM_XXXL = 72.dp

/** 100.dp*/
val DIM_XXXL_PLUS = 100.dp

/** 200.dp*/
val DIM_BIG_TWO = 200.dp

// durations
/** 150 */
const val TAB_FADE_IN_DURATION = 150

/** 100 */
const val TAB_FADE_IN_DELAY_DURATION = 100

/** 100 */
const val TAB_FADE_OUT_DURATION = 100

/** 200 */
const val ACCORDION_ANIMATION_DURATION = 200

// opacity
/** 0.6f */
const val TAB_INACTIVE_OPACITY = 0.6f

/** buttons percentage of RoundCornerShape  */
const val BUTTON_CORNER_SHAPE = 25

const val MAINTAINER_APP: String = "MAINTAINER_APP"
const val IS_APP_CONFIGURED_TAG = "isAppConfigured"
const val IS_APP_CONFIGURED_DEFAULT = false

val DEFAULT_STEP = Step(order = 0, title = "", taskId = 0)

val ICON_LIST = listOf(
    R.drawable.question_mark_48px,
    R.drawable.bathtub_48px,
    R.drawable.directions_car_48px,
    R.drawable.dishwasher_gen_48px,
    R.drawable.computer_48px,
    R.drawable.coffee_maker_48px,
    R.drawable.local_laundry_service_48px,
    R.drawable.kitchen_48px,
    R.drawable.pedal_bike_48px
)
