package com.hrudhaykanth116.core.ui.notification

actual class NotificationScheduler {
    // TODO: Implement using UNUserNotificationCenter
    // - Request notification permission on first use
    // - Use UNTimeIntervalNotificationTrigger for scheduling
    // - Store taskId as notification identifier for cancellation
    actual fun scheduleReminder(taskId: String, title: String, triggerTimeMillis: Long) {
    }

    // TODO: Cancel notification by identifier using UNUserNotificationCenter
    actual fun cancelReminder(taskId: String) {
    }

    // TODO: Remove all pending notifications
    actual fun cancelAllReminders() {
    }
}
