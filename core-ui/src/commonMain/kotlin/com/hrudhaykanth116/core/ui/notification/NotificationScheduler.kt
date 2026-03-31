package com.hrudhaykanth116.core.ui.notification

expect class NotificationScheduler {
    fun scheduleReminder(taskId: String, title: String, triggerTimeMillis: Long)
    fun cancelReminder(taskId: String)
    fun cancelAllReminders()
}
