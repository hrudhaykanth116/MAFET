package com.hrudhaykanth116.core.ui.notification

expect class NotificationScheduler : INotificationScheduler {
    override fun scheduleReminder(taskId: String, title: String, triggerTimeMillis: Long)
    override fun cancelReminder(taskId: String)
    override fun cancelAllReminders()
}
