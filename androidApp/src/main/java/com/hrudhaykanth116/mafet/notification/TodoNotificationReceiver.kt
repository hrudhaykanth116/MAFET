package com.hrudhaykanth116.mafet.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import co.touchlab.kermit.Logger
import com.hrudhaykanth116.core.ui.notification.NotificationScheduler

class TodoNotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != NotificationScheduler.ACTION_SHOW_NOTIFICATION) {
            return
        }

        val taskId = intent.getStringExtra(NotificationScheduler.EXTRA_TASK_ID)
        val taskTitle = intent.getStringExtra(NotificationScheduler.EXTRA_TASK_TITLE)

        if (taskId == null || taskTitle == null) {
            Logger.w { "TodoNotificationReceiver: Missing task data" }
            return
        }

        Logger.d { "TodoNotificationReceiver: Showing notification for task $taskId" }
        NotificationHelper.showTaskReminder(context, taskId, taskTitle)
    }
}
