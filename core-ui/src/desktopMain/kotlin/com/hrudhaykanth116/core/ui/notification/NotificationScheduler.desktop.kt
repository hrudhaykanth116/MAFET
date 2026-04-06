package com.hrudhaykanth116.core.ui.notification

actual class NotificationScheduler : INotificationScheduler {
    // TODO: Implement desktop notifications using java.awt.SystemTray or platform-specific APIs
    actual override fun scheduleReminder(taskId: String, title: String, triggerTimeMillis: Long) {
    }

    actual override fun cancelReminder(taskId: String) {
    }

    actual override fun cancelAllReminders() {
    }
}
