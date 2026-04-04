Tech features to be implemented in the project.
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


Features:

* Home
    * Centralised place with most important&relevant information from each feature.

* TODOLIST

* Journal
    * List of entries.

* Weather
    * Refresh ability and lastRefreshedTime ui element.

* Tv
    * List of my tv list that contains the tv series currently being watched or watched already.
        * This list allows me to track last watched episode, fav episodes, fav scene time interval etc..
    * Status: In progress, Watched, Wishlist-ed.

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
