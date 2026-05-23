package com.hrudhaykanth116.mafet.utils.appicon

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager

object AppIconManager {

    fun setIcon(
        context: Context,
        targetIcon: AppIcon
    ) {

        if (getCurrentIcon(context) == targetIcon) {
            return
        }

        val packageManager = context.packageManager

        AppIcon.entries.forEach { icon ->

            val state =
                if (icon == targetIcon) {
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED
                } else {
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED
                }

            packageManager.setComponentEnabledSetting(
                ComponentName(
                    context,
                    icon.aliasName
                ),
                state,
                PackageManager.DONT_KILL_APP
            )
        }
    }

    /**
     * Get the current app icon and set it when app is launched. Preferably in Application class.
     * This is required because when the app is killed, the icon will be reset to default and we need to set it back to the current icon.
     */
    fun getCurrentIcon(
        context: Context
    ): AppIcon {

        val packageManager = context.packageManager

        return AppIcon.entries.firstOrNull { icon ->

            packageManager.getComponentEnabledSetting(
                ComponentName(
                    context,
                    icon.aliasName
                )
            ) == PackageManager.COMPONENT_ENABLED_STATE_ENABLED

        } ?: AppIcon.DEFAULT
    }
}