/*
 * Created by Christopher Becker on 09/02/2024, 11:48
 * Copyright (c) 2024. All rights reserved.
 * Last modified 09/02/2024, 11:48
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

package de.chrisbecker386.maintainer.service

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import de.chrisbecker386.maintainer.MainActivity
import de.chrisbecker386.maintainer.R
import de.chrisbecker386.maintainer.data.entity.Task
import de.chrisbecker386.maintainer.service.interfaces.NotificationService

class ReminderNotificationService(private val context: Context) :
    NotificationService<List<Task>?> {
    companion object {
        const val REMINDER_CHANNEL_ID = "maintainer_reminder_channel"
        const val DISPLAYED_NAME = "Reminder"
        const val IMPORTANCE_LEVEL = NotificationManager.IMPORTANCE_DEFAULT
        const val DESCRIPTION = "Used to remind you of upcoming tasks"
        const val TITLE = "Reminder by Maintainer"
    }

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    override fun showOpenTasksNotification(data: List<Task>?) {
        val activityIntent = Intent(context, MainActivity::class.java)
        val activityPendingIntent =
            PendingIntent.getActivity(context, 1, activityIntent, PendingIntent.FLAG_IMMUTABLE)
        val notification = NotificationCompat.Builder(context, REMINDER_CHANNEL_ID)
            .setSmallIcon(R.drawable.schedule)
            .setContentTitle(TITLE)
            .setContentText(formatOpenTasks(data))
            .setContentIntent(activityPendingIntent)
            .build()
        notificationManager.notify(1, notification)
    }

    private fun formatOpenTasks(data: List<Task>?): String {
        var text = ""
        data?.forEach { task -> text += "${task.getFormattedTask()}\n" }
        if (text.isEmpty()) text = "no open tasks"
        return text
    }
}
