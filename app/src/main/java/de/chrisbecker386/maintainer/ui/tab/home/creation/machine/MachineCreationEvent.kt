/*
 * Created by Christopher Becker on 16/12/2023, 15:21
 * Copyright (c) 2023. All rights reserved.
 * Last modified 16/12/2023, 15:21
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

package de.chrisbecker386.maintainer.ui.tab.home.creation.machine

import androidx.annotation.DrawableRes
import de.chrisbecker386.maintainer.data.entity.Machine

interface MachineCreationEvent {
    data class TitleChange(val title: String?) : MachineCreationEvent
    data class SubtitleChange(val subtitle: String?) : MachineCreationEvent
    data class ImageChange(@DrawableRes val imageRes: Int?) : MachineCreationEvent
    data class MachineConfirm(val machine: Machine) : MachineCreationEvent
}
