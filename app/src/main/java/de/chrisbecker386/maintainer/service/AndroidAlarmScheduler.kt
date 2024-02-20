/*
 * Created by Christopher Becker on 15/02/2024, 18:46
 * Copyright (c) 2024. All rights reserved.
 * Last modified 15/02/2024, 18:46
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

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import de.chrisbecker386.maintainer.data.model.AlarmReminder
import de.chrisbecker386.maintainer.service.interfaces.AlarmScheduler
import de.chrisbecker386.maintainer.ui.theme.ALARM_MESSAGE
import de.chrisbecker386.maintainer.ui.theme.ALARM_TITLE

class AndroidAlarmScheduler(private val context: Context) : AlarmScheduler {
    private val tag = AndroidAlarmScheduler::class.simpleName

    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    override fun schedule(item: AlarmReminder) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(ALARM_TITLE, item.title)
            putExtra(ALARM_MESSAGE, item.message)
        }
        val itemHashCode = item.title.hashCode()

        Log.d(tag, "schedule hasCode: $itemHashCode")

        alarmManager.setRepeating(
            AlarmManager.RTC,
            item.firstExecutionTime * 1000,
            item.repeatInterval,
            PendingIntent.getBroadcast(
                context,
                itemHashCode,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    override fun cancel(item: AlarmReminder) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(ALARM_TITLE, item.title)
            putExtra(ALARM_MESSAGE, item.message)
        }

        val itemHashCode = item.title.hashCode()

        Log.d(tag, "cancel hasCode: $itemHashCode")

        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                itemHashCode,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
}
