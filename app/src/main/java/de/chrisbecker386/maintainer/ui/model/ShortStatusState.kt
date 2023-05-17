/*
 * Created by Christopher Becker on 17/05/2023, 09:25
 * Copyright (c) 2023. All rights reserved.
 * Last modified 17/05/2023, 09:25
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

data class ShortStatusState(
    val numerator: Int,
    val denominator: Int
) {
    override fun toString(): String = "$numerator/$denominator"

    fun getProgress(): Float {
        return if (denominator <= 0) {
            0f
        } else {
            numerator.toFloat() / denominator.toFloat()
        }
    }
}
