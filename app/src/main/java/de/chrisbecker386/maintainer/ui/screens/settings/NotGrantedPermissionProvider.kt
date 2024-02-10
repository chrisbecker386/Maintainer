/*
 * Created by Christopher Becker on 12/02/2024, 12:48
 * Copyright (c) 2024. All rights reserved.
 * Last modified 12/02/2024, 12:48
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

import android.content.Context
import android.os.Build
import android.util.Log

class NotGrantedPermissionProvider(
    val context: Context,
    val permissions: List<MaintainerPermission>
) {

    fun getPermissionsToRequest(): List<MaintainerPermission> {
        val notGrantedPermissions = mutableListOf<MaintainerPermission>()
        permissions.forEach { permission ->
            val minSdkForPermissionRequest = permission.minSDK <= Build.VERSION.SDK_INT
            Log.d("minSDK", minSdkForPermissionRequest.toString())
            val maxSdkForPermissionRequest =
                ((permission.maxSDK ?: Build.VERSION.SDK_INT) >= Build.VERSION.SDK_INT)
            Log.d("maxSDK", maxSdkForPermissionRequest.toString())
            val permissionNotGrantedForRequest =
                isNotPermissionGranted(context.checkSelfPermission(permission.manifestReference))
            Log.d(
                "Not granted",
                permissionNotGrantedForRequest.toString()
            )
            if (
                minSdkForPermissionRequest &&
                maxSdkForPermissionRequest &&
                permissionNotGrantedForRequest
            ) {
                notGrantedPermissions.add(permission)
            }
        }
        return notGrantedPermissions.toList()
    }

    private fun isNotPermissionGranted(value: Int): Boolean = value == -1
}
