/*
 * Created by Christopher Becker on 12/02/2024, 21:29
 * Copyright (c) 2024. All rights reserved.
 * Last modified 12/02/2024, 21:29
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

package de.chrisbecker386.maintainer.ui.screens.settings

data class SettingsData(
    val permissionsToRequest: List<MaintainerPermission> = emptyList(),
    val isPermissionQueueEmpty: Boolean = false
)
