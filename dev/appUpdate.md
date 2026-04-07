# In-App Update

## How It Works

MAFET uses the **Google Play In-App Update API** to prompt users to update the app without leaving it.

Two update flows are supported, driven by the **update priority** set per release in Play Console:

| Priority | Flow                | Behaviour                                                                       |
|----------|---------------------|---------------------------------------------------------------------------------|
| 4 – 5    | IMMEDIATE (force)   | Fullscreen overlay blocks the app until the user updates                        |
| 1 – 3    | FLEXIBLE (optional) | Update downloads silently in background; a snackbar prompts restart when ready  |
| 0        | No update prompt    | Nothing is shown                                                                |

The check runs every time the app comes to the foreground (`onResume`), so a user who dismisses and re-opens will be prompted again.

---

## Key Classes

- `InAppUpdateManager` — `androidApp/.../update/InAppUpdateManager.kt`
  - Wraps `AppUpdateManager` from the Play library
  - Exposes `events: SharedFlow<InAppUpdateEvent>` for the snackbar trigger
  - `checkAndStartUpdate(activity)` — called from `MainActivity.onResume()`
  - `completeFlexibleUpdate()` — called when user taps "Restart" on the snackbar
  - `unregisterListeners()` — called from `MainActivity.onStop()` to prevent leaks

- `InAppUpdateEvent` — sealed interface with one value: `FlexibleUpdateReadyToInstall`

---

## Setting Update Priority in Play Console

1. Open **Play Console → Your App → Release → Production** (or any track)
2. Create or edit a release
3. Under **Release details**, set **In-app update priority** (0–5)
4. Publish the release

The device will see the priority the next time it syncs with the Play Store (usually within a few hours).

---

## Testing Internally

### Option 1 — Internal Testing Track (recommended, on a real device)

1. Build the app with a **higher `versionCode`** than what's installed on the device
2. Upload it to **Play Console → Internal Testing track**
3. Set the **In-app update priority** on that release (e.g. 5 for force, 2 for optional)
4. On the device, install the **lower versionCode** build via ADB:
   ```bash
   adb install -r app-debug.apk
   ```
5. Open the app — the update prompt should appear

> The device must be signed into a Google account that is a tester on the Internal Testing track.

### Option 2 — FakeAppUpdateManager (automated / no Play Store needed)

The Play library ships a `FakeAppUpdateManager` in the `play:app-update-testing` artifact. It simulates the full update flow in tests or debug builds.

```kotlin
val fakeManager = FakeAppUpdateManager(context)
fakeManager.setUpdateAvailable(2)        // higher versionCode available
fakeManager.setUpdatePriority(5)         // 5 = force update

// Inject into InAppUpdateManager via constructor
val updateManager = InAppUpdateManager(context, fakeManager)
```

To simulate a completed flexible download:
```kotlin
fakeManager.downloadStarts()
fakeManager.downloadCompletes()
// -> InAppUpdateEvent.FlexibleUpdateReadyToInstall will be emitted
```

---

## Checklist Before Going Live

- [ ] `versionCode` is incremented in `androidApp/build.gradle.kts`
- [ ] Release is uploaded to the correct Play Console track
- [ ] In-app update priority is set on the release
- [ ] Tested on a physical device via Internal Testing track
