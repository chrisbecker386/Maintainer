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

package de.chrisbecker386.maintainer.ui.screens.settings

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.chrisbecker386.maintainer.data.model.AlarmReminder
import de.chrisbecker386.maintainer.data.model.DataResourceState
import de.chrisbecker386.maintainer.data.model.RepeatFrequency
import de.chrisbecker386.maintainer.service.AndroidAlarmScheduler
import de.chrisbecker386.maintainer.ui.component.BaseButton
import de.chrisbecker386.maintainer.ui.component.PermissionDialog
import de.chrisbecker386.maintainer.ui.component.basic.CircularLoadIndicator
import de.chrisbecker386.maintainer.ui.component.basic.MessageFullScreen
import de.chrisbecker386.maintainer.ui.component.editables.RepeatFrequencyEditor
import de.chrisbecker386.maintainer.ui.theme.ALARM_TITLE_NOTIFICATION
import de.chrisbecker386.maintainer.ui.theme.DIM_M
import de.chrisbecker386.maintainer.ui.theme.DIM_XS
import java.time.LocalDateTime
import java.time.ZoneId

@Composable
fun SettingsScreen() {
    val viewModel = hiltViewModel<SettingsViewModel>()

    val state by viewModel.settingsState.collectAsStateWithLifecycle()

    when (state) {
        is DataResourceState.Error -> {
            MessageFullScreen(
                title = "Error",
                message = (state as DataResourceState.Error).message,
                onClick = { viewModel.onEvent(SettingsEvent.AcceptError) }
            )
        }

        is DataResourceState.Success -> SettingsInternal(
            state = (state as DataResourceState.Success<SettingsData>).data,
            onEvent = viewModel::onEvent
        )

        is DataResourceState.Loading -> {
            CircularLoadIndicator()
        }

        is DataResourceState.Finished -> TODO()
    }
}

@Composable
fun SettingsInternal(
    state: SettingsData,
    onEvent: (SettingsEvent) -> Unit = {}
) {
    val activity = LocalContext.current as Activity

    val multiplePermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = {
            state.permissionsToRequest.forEach { permission ->
                onEvent(
                    SettingsEvent.OnPermissionResult(
                        permission = permission,
                        isGranted = it[permission.manifestReference] == true
                    )
                )
            }
        }
    )

    var firstNotifyExecution by remember {
        mutableLongStateOf(LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond())
    }
    var repeatFrequency by remember { mutableStateOf(Pair(RepeatFrequency.WEEKLY.value, 1)) }

    var showPermissionRequest by remember {
        mutableStateOf(false)
    }

    fun getCurrentAlarmItem(): AlarmReminder = AlarmReminder(
        firstExecutionTime = firstNotifyExecution,
        repeatInterval = (repeatFrequency.first * repeatFrequency.second),
        title = ALARM_TITLE_NOTIFICATION,
        message = AlarmReminder.getInfo(
            firstNotifyExecution,
            (repeatFrequency.first * repeatFrequency.second)
        )
    )

    val scheduler = AndroidAlarmScheduler(LocalContext.current)

    Column(
        Modifier
            .fillMaxSize()
            .padding(start = DIM_XS, end = DIM_XS)
    ) {
        RepeatFrequencyEditor(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    enabled = !state.isPermissionQueueEmpty,
                    onClickLabel = null,
                    role = null,
                    onClick = { showPermissionRequest = true }
                ),
            enable = state.isPermissionQueueEmpty,
            title = "Notification",
            startDateTime = firstNotifyExecution,
            repeatFrequencyAndTact = repeatFrequency,
            onDateTimeChange = { firstNotifyExecution = it },
            onRepeatFrequencyAndTactChange = { repeatFrequency = it }
        ) {
            Spacer(modifier = Modifier.size(DIM_XS))
            Row(Modifier.fillMaxWidth()) {
                BaseButton(
                    text = "start",
                    onClick = { getCurrentAlarmItem().let(scheduler::schedule) }
                )
                Spacer(modifier = Modifier.size(DIM_M))
                BaseButton(
                    text = "cancel",
                    onClick = { getCurrentAlarmItem().let(scheduler::cancel) }
                )
            }
        }

        if (showPermissionRequest && !state.isPermissionQueueEmpty) {
            state.permissionsToRequest.forEach {
                PermissionDialog(
                    permission = it,
                    isPermanentlyDeclined = shouldShowRequestPermissionRationale(
                        activity,
                        it.manifestReference
                    ),
                    onDismiss = {
                        onEvent(SettingsEvent.RemovePermissionFromRequestQueue)
                    },
                    onAccept = {
                        onEvent(SettingsEvent.RemovePermissionFromRequestQueue)
                        multiplePermissionResultLauncher.launch(arrayOf(it.manifestReference))
                    },
                    onGoToAppSettingsClick = {
                        onEvent(SettingsEvent.RemovePermissionFromRequestQueue)
                        activity.openAppSettings()
                    }
                )
            }
        }
    }
}

fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}
