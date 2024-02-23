/*
 * Created by Christopher Becker on 15/02/2024, 18:57
 * Copyright (c) 2024. All rights reserved.
 * Last modified 15/02/2024, 18:57
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

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import de.chrisbecker386.maintainer.data.utility.goAsync
import de.chrisbecker386.maintainer.domain.repository.MaintainerRepository
import de.chrisbecker386.maintainer.ui.theme.ALARM_MESSAGE
import de.chrisbecker386.maintainer.ui.theme.ALARM_TITLE
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {
    private val tag = "AlarmReceiver"

    @Inject
    lateinit var repository: MaintainerRepository

    override fun onReceive(context: Context?, intent: Intent?) {
        val service = ReminderNotificationService(context as Context)

        val title = intent?.getStringExtra(ALARM_TITLE) ?: return
        val message = intent.getStringExtra(ALARM_MESSAGE)

        goAsync {
            val tasks = repository.getAllOpenTasks(Calendar.getInstance().timeInMillis)
            service.showOpenTasksNotification(tasks)
            Log.d(tag, "Alarm triggered $title, $message $tasks")
        }
    }
}
