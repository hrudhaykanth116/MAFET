package com.hrudhaykanth116.core.ui.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import co.touchlab.kermit.Logger

actual class NotificationScheduler(private val context: Context) : INotificationScheduler {

    private val alarmManager: AlarmManager
        get() = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    actual override fun scheduleReminder(taskId: String, title: String, triggerTimeMillis: Long) {
        if (triggerTimeMillis <= System.currentTimeMillis()) {
            Logger.d { "NotificationScheduler: Trigger time is in the past, skipping" }
            return
        }

        val intent = Intent(ACTION_SHOW_NOTIFICATION).apply {
            setPackage(context.packageName)
            putExtra(EXTRA_TASK_ID, taskId)
            putExtra(EXTRA_TASK_TITLE, title)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            taskId.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (alarmManager.canScheduleExactAlarms()) {
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        triggerTimeMillis,
                        pendingIntent
                    )
                    Logger.d { "NotificationScheduler: Scheduled reminder for task $taskId at $triggerTimeMillis" }
                } else {
                    alarmManager.setAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        triggerTimeMillis,
                        pendingIntent
                    )
                    Logger.d { "NotificationScheduler: Scheduled inexact reminder for task $taskId" }
                }
            } else {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    triggerTimeMillis,
                    pendingIntent
                )
                Logger.d { "NotificationScheduler: Scheduled reminder for task $taskId at $triggerTimeMillis" }
            }
        } catch (e: SecurityException) {
            Logger.e(e) { "NotificationScheduler: Failed to schedule exact alarm" }
            alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTimeMillis, pendingIntent)
        }
    }

    actual override fun cancelReminder(taskId: String) {
        val intent = Intent(ACTION_SHOW_NOTIFICATION).apply {
            setPackage(context.packageName)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            taskId.hashCode(),
            intent,
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
        )

        pendingIntent?.let {
            alarmManager.cancel(it)
            it.cancel()
            Logger.d { "NotificationScheduler: Cancelled reminder for task $taskId" }
        }
    }

    actual override fun cancelAllReminders() {
        Logger.d { "NotificationScheduler: Cancel all reminders not fully implemented" }
    }

    companion object {
        const val ACTION_SHOW_NOTIFICATION = "com.hrudhaykanth116.mafet.SHOW_TODO_NOTIFICATION"
        const val EXTRA_TASK_ID = "task_id"
        const val EXTRA_TASK_TITLE = "task_title"
    }
}
