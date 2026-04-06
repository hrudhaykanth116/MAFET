package com.hrudhaykanth116.todo.testutils

import com.hrudhaykanth116.core.ui.notification.INotificationScheduler

class FakeNotificationScheduler : INotificationScheduler {
    override fun scheduleReminder(taskId: String, title: String, triggerTimeMillis: Long) {}
    override fun cancelReminder(taskId: String) {}
    override fun cancelAllReminders() {}
}
