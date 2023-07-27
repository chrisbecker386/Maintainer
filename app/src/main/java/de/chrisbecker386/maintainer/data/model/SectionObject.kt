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

package de.chrisbecker386.maintainer.data.model

import androidx.annotation.DrawableRes
import de.chrisbecker386.maintainer.R
import de.chrisbecker386.maintainer.data.model.interfaces.ItemObject

data class SectionObject(
    override val id: Int,
    override val title: String,
    @DrawableRes
    val graphic: Int? = R.drawable.question_mark_48px,
    override val list: List<MachineObject> = emptyList()
) : ItemObject {
    fun toSectionGridItem(): GridItemData = GridItemData(
        this.id,
        this.title,
        this.graphic
    )
}
