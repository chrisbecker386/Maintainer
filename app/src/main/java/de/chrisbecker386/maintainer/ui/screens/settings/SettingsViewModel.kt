/*
 * Created by Christopher Becker on 10/02/2024, 21:44
 * Copyright (c) 2024. All rights reserved.
 * Last modified 10/02/2024, 21:44
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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import de.chrisbecker386.maintainer.data.model.DataResourceState
import de.chrisbecker386.maintainer.data.model.DataResourceState.Companion.idle
import de.chrisbecker386.maintainer.data.model.DataResourceState.Companion.loading
import de.chrisbecker386.maintainer.data.model.DataResourceState.Companion.success
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(@ApplicationContext context: Context) : ViewModel() {

    private val _permissionQueue = MutableStateFlow<List<MaintainerPermission>>(emptyList())
    private val _isPermissionQueueEmpty = _permissionQueue.mapLatest { queue -> queue.isEmpty() }
    private val _settingsDataDefault = MutableStateFlow(SettingsData())

    private val _settingsData =
        combine(
            _settingsDataDefault,
            _permissionQueue,
            _isPermissionQueueEmpty
        ) { settingsData, permissionQueue, isPermissionQueueEmpty ->
            settingsData.copy(
                permissionsToRequest = permissionQueue,
                isPermissionQueueEmpty = isPermissionQueueEmpty
            )
        }
    private val _error = MutableStateFlow<Throwable?>(null)
    private val _isLoading = MutableStateFlow(false)
    private val _isSuccess = MutableStateFlow(false)

    val settingsState: StateFlow<DataResourceState<SettingsData>> = combine(
        _settingsData,
        _error,
        _isLoading,
        _isSuccess
    ) { settingsData, error, isLoading, isSuccess ->
        when {
            isLoading -> loading(data = null)
            error != null -> DataResourceState.error(error.message ?: "", error)
            isSuccess -> success()
            else -> idle(settingsData)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), loading(data = null))

    init {
        viewModelScope.launch {
            _permissionQueue.emit(
                NotGrantedPermissionProvider(context, MAINTAINER_PERMISSIONS)
                    .getPermissionsToRequest()
            )
        }
    }
    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.AcceptError -> acceptError()
            is SettingsEvent.RemovePermissionFromRequestQueue -> removePermissionFromRequestQueue()
            is SettingsEvent.OnPermissionResult -> onPermissionResult(
                event.permission,
                event.isGranted
            )
        }
    }

    private fun removePermissionFromRequestQueue() {
        val newList: List<MaintainerPermission> = if (_permissionQueue.value.isNotEmpty()) {
            val list = _permissionQueue.value.toMutableList()
            list.removeFirst()
            list.toList()
        } else {
            emptyList<MaintainerPermission>()
        }
        viewModelScope.launch { _permissionQueue.emit(newList) }
    }

    private fun onPermissionResult(permission: MaintainerPermission, isGranted: Boolean) {
        if (!_permissionQueue.value.contains(permission) && !isGranted) {
            val newList = _permissionQueue.value.toMutableList()
            newList.add(permission)
            newList.toList()
            viewModelScope.launch { _permissionQueue.emit(newList) }
        }
    }

    private fun acceptError() = viewModelScope.launch { _error.emit(null) }
}
