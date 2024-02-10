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
import de.chrisbecker386.maintainer.data.entity.TaskCompletedDate
import de.chrisbecker386.maintainer.data.entity.relation.TaskWithStepsCompletes
import de.chrisbecker386.maintainer.data.model.dummy.devSteps
import de.chrisbecker386.maintainer.data.model.dummy.devTasks
import de.chrisbecker386.maintainer.ui.theme.ALARM_MESSAGE
import de.chrisbecker386.maintainer.ui.theme.ALARM_TITLE

class AlarmReceiver : BroadcastReceiver() {

    companion object { const val TAG = "AlarmReceiver" }

    override fun onReceive(context: Context?, intent: Intent?) {
        val service = ReminderNotificationService(context as Context)

        val title = intent?.getStringExtra(ALARM_TITLE) ?: return
        val message = intent.getStringExtra(ALARM_MESSAGE)

        service.showOpenTasksNotification(
            listOf(
                TaskWithStepsCompletes(
                    task = devTasks[0],
                    steps = devSteps.filter { it.taskId == devTasks[0].id },
                    completes = listOf(TaskCompletedDate(1, 12312312312L, devTasks[0].id))
                )
            )
        )

        Log.d(TAG, "Alarm triggered $title, $message")
    }
}
