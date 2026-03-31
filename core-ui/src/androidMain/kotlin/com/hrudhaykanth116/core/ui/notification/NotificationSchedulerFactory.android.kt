package com.hrudhaykanth116.core.ui.notification

import org.koin.core.component.KoinComponent
import org.koin.core.component.get

actual fun getNotificationScheduler(): NotificationScheduler {
    return object : KoinComponent {}.get<android.content.Context>().let { NotificationScheduler(it) }
}
