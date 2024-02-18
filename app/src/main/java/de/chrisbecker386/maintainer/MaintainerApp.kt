/*
 * Created by Christopher Becker on 09/05/2023, 15:09
 * Copyright (c) 2023. All rights reserved.
 * Last modified 09/05/2023, 15:09
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

package de.chrisbecker386.maintainer

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import de.chrisbecker386.maintainer.service.ReminderNotificationService

@HiltAndroidApp
class MaintainerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        val notifyService = createNotificationReminderChannel()
    }

    private fun createNotificationReminderChannel() {
        val reminderChannel =
            with(ReminderNotificationService) {
                NotificationChannel(
                    REMINDER_CHANNEL_ID,
                    DISPLAYED_NAME,
                    IMPORTANCE_LEVEL
                )
            }
        reminderChannel.description = ReminderNotificationService.DESCRIPTION
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(reminderChannel)
    }
}
