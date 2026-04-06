package com.hrudhaykanth116.core.ui.notification

interface INotificationScheduler {
    fun scheduleReminder(taskId: String, title: String, triggerTimeMillis: Long)
    fun cancelReminder(taskId: String)
    fun cancelAllReminders()
}
