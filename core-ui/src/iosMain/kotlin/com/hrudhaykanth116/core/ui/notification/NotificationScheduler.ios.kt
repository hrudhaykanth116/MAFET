package com.hrudhaykanth116.core.ui.notification

actual class NotificationScheduler : INotificationScheduler {
    // TODO: Implement using UNUserNotificationCenter
    // - Request notification permission on first use
    // - Use UNTimeIntervalNotificationTrigger for scheduling
    // - Store taskId as notification identifier for cancellation
    actual override fun scheduleReminder(taskId: String, title: String, triggerTimeMillis: Long) {
    }

    // TODO: Cancel notification by identifier using UNUserNotificationCenter
    actual override fun cancelReminder(taskId: String) {
    }

    // TODO: Remove all pending notifications
    actual override fun cancelAllReminders() {
    }
}
