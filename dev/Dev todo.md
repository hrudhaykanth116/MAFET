Tech features to be implemented in the project.

Implement Network Monitor in NetworkDataSource.
Handle api error cases.


iOS Setup:
* Wire secrets.xcconfig in Xcode (API keys won't work on iOS until done)
    * Open iosApp/iosApp.xcodeproj in Xcode
    * Click project root → Info tab → Configurations
    * Set secrets.xcconfig for both Debug and Release under iosApp target
    * File location: iosApp/secrets.xcconfig (see secrets.xcconfig.example)

iOS Missing Implementations:
* Remote Config KMP Migration
    * Currently: RemoteConfigManager lives in androidApp (Firebase Android SDK only)
    * Needs: Migrate to core-common using dev.gitlive:firebase-config (GitLive KMP SDK)
    * Why: iOS can't share the current implementation; GitLive wraps both native SDKs under a single commonMain API
    * Plan:
        - Add dev.gitlive:firebase-config to core-common
        - Move RemoteConfigManager + RemoteAppConfig + AppGateConfig to core-common/commonMain
        - Move FeatureConfig to core-common/commonMain (or keep in composeApp)
        - AppGateDialog stays in androidApp; iOS gets its own SwiftUI/Compose equivalent
        - Remove firebase-config from androidApp once migrated
    * Complexity: Medium (2-4 hours)


* NotificationScheduler (core-ui/src/iosMain/.../notification/NotificationScheduler.ios.kt)
    * Currently: Empty stub methods (no-op)
    * Needs: UNUserNotificationCenter implementation
    * Tasks:
        - Request notification permission using UNUserNotificationCenter.requestAuthorization()
        - Schedule notifications with UNTimeIntervalNotificationTrigger
        - Use taskId as notification identifier for cancellation
        - Handle cancelReminder() and cancelAllReminders()
* Complexity: Medium (4-6 hours)

Testing (Newly Added Features):
* WorkManager Background Sync
    * Create a todo while offline, observe sync status
    * Enable network and verify task syncs within 15 minutes
    * Check Logcat for "TodoSyncWorker" logs
* Local Notifications
    * Create todo with targetTime set to 1-2 minutes from now
    * Close app completely
    * Verify notification appears at scheduled time
    * Tap notification and confirm app opens


Tech:
 * Navigation3

APIS:

Tools/Libs:
  *Recompositions debugger: https://github.com/theapache64/rebugger (Plugin: https://github.com/theapache64/rebugger-plugin/)