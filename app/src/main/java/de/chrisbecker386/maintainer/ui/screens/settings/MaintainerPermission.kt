/*
 * Created by Christopher Becker on 10/02/2024, 21:50
 * Copyright (c) 2024. All rights reserved.
 * Last modified 10/02/2024, 21:50
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

import android.os.Build

interface MaintainerPermission {
    val permissionName: String
    val manifestReference: String
    val minSDK: Int
    val maxSDK: Int?
    val description: String
    val permanentDeclinedText: String
}

object LocalNotification : MaintainerPermission {
    override val permissionName: String = "POST_NOTIFICATIONS"
    override val manifestReference = "android.permission.POST_NOTIFICATIONS"
    override val minSDK = Build.VERSION_CODES.TIRAMISU
    override val maxSDK = null
    override val description = "This App needs permission to send you notifications"
    override val permanentDeclinedText =
        "Notification permission is permanently declined\nPermission can be granted in app settings"
}

//    TIME("TIME TODO");

val MAINTAINER_PERMISSIONS = listOf(LocalNotification)
