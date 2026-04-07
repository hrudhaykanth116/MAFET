Tech features to be implemented in the project.

README:
* Add screenshots to README (## Demo & Screenshots section is a placeholder)
* CI/CD pipeline with github actions.
* Sonarqube check
* *

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

* AIScreen (features/ai/src/iosMain/.../AIScreen.ios.kt)
    * Currently: Shows "AI feature coming soon to iOS" placeholder
    * Needs: Firebase Vertex AI or alternative AI SDK for iOS
    * Note: Android version also shows placeholder, so this is intentional for now
    * Complexity: High (depends on AI SDK choice)

* AuthNavigation (composeApp/src/iosMain/.../PlatformNavigation.ios.kt)
    * Currently: Shows "Auth coming soon to iOS" placeholder
    * Needs: Firebase Authentication KMP support or alternative auth solution
    * Blocked by: Firebase Auth doesn't have official KMP support yet
    * Complexity: High (2-3 days if migrating to KMP-compatible auth)

* GamesNavigation (composeApp/src/iosMain/.../PlatformNavigation.ios.kt)
    * Currently: Shows "Games coming soon to iOS" placeholder
    * Needs: Port entire games module to KMP
    * Note: Games module uses Android-specific sprite/game libraries
    * Complexity: High (3-5 days)

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
* Shared Element Transitions
    * Tap a todo item in the list
    * Observe card animating smoothly to detail/edit screen
    * Press back and verify card animates back to list position


Splash screen:
https://www.youtube.com/watch?v=Oy0oXwv3kSc

Tech:
 * Navigation3

APIS:

Compose UI:
  RenderEffect: https://blog.canopas.com/how-to-use-render-effects-in-jetpack-compose-for-stunning-visuals-01287d7f00db

Tools/Libs:
  *Recompositions debugger: https://github.com/theapache64/rebugger (Plugin: https://github.com/theapache64/rebugger-plugin/)
  *Konsist. Lint checks


* Interface mapper generic for layer to layer models conversion
