package com.hrudhaykanth116.core.ui.notification

actual class NotificationScheduler {
    actual fun scheduleReminder(taskId: String, title: String, triggerTimeMillis: Long) {
    }

    actual fun cancelReminder(taskId: String) {
    }

    actual fun cancelAllReminders() {
    }
}
