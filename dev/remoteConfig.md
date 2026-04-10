# Remote Config — App Gate System

## Overview

The app gate system allows server-driven blocking dialogs to be shown to users before they reach the HomeScreen. Gates are configured via Firebase Remote Config and evaluated entirely on the client side.

---

## Architecture

```
Firebase Remote Config
  app_gate_force         ─┐
  app_gate_maintenance   ─┤→ RemoteConfigDataSource (Android/iOS/Desktop)
  app_gate_message       ─┘         ↓
  feature_todo / ...            RemoteConfigRepository
                                     ↓
                              GetRemoteConfigUseCase  ← priority resolution
                                     ↓
                               AppViewModel (state)
                                     ↓
                             AppGateDialog (UI / FlowRow buttons)
```

### Button action values

Any string with a valid URI scheme (`scheme:...`) is treated as a URI and opened via `LocalUriHandler` (Android: `Intent.ACTION_VIEW`). Only `"dismiss"` is a special keyword — everything else without a URI scheme is silently ignored.

| Action value | Behaviour |
|---|---|
| `"dismiss"` | Closes the gate dialog, user reaches HomeScreen |
| `"https://..."` | Opens in system browser |
| `"http://..."` | Opens in system browser |
| `"mailto:user@example.com"` | Opens email client with pre-filled recipient |
| `"tel:+919876543210"` | Opens phone dialer with pre-filled number |
| `"geo:12.97,77.59"` | Opens maps at coordinates |
| `"market://details?id=com.pkg"` | Opens Play Store app page directly |
| `"mafet://feature/todo"` | Launches registered in-app deep link |
| `"intent://..."` | Android Intent URI (platform-specific) |
| Bare string with no scheme (e.g. `"play.google.com"`) | Silently ignored — no crash, no state change |

> **Detection rule:** Implemented in `core-common/.../utils/url/UrlValidator.kt` using RFC 3986 URI scheme regex `^[a-zA-Z][a-zA-Z0-9+\-.]*:.*`. Any action matching this pattern is forwarded to `LocalUriHandler.openUri()`.

> **Deep links:** To handle `mafet://` deep links within the app, register an `<intent-filter>` in `AndroidManifest.xml` for the scheme. The system will route it to the correct Activity/destination.

---

## Firebase Remote Config Keys

### Gate keys (JSON, Data type)

| Key | Targets |
|---|---|
| `app_gate_force` | Old app versions — use a Firebase Condition on `App version` |
| `app_gate_maintenance` | All users (set default to enabled during downtime) |
| `app_gate_message` | Specific audiences — use a Firebase Condition on Audience |


**Example — force update for v1.x users:**
```json
{
  "is_enabled": true,
  "title": "Update Required",
  "message": "This version is no longer supported.",
  "buttons": [
    { "text": "Update Now", "action": "https://play.google.com/store/apps/details?id=com.hrudhaykanth116.mafet" }
  ]
}
```

**Example — maintenance (no buttons, non-dismissable):**
```json
{
  "is_enabled": true,
  "title": "Under Maintenance",
  "message": "We'll be back shortly. Thank you for your patience.",
  "buttons": []
}
```

**Example — dismissable message with two buttons:**
```json
{
  "is_enabled": true,
  "title": "Beta Notice",
  "message": "You're using a beta build. Things may break.",
  "buttons": [
    { "text": "Got it", "action": "dismiss" },
    { "text": "Learn more", "action": "https://yoursite.com/beta" }
  ]
}
```

### Feature flag keys (Boolean type)

| Key | Default |
|---|---|
| `feature_todo` | `true` |
| `feature_journal` | `true` |
| `feature_ai` | `true` |
| `feature_weather` | `true` |
| `feature_watchlist` | `true` |
| `feature_media` | `true` |

---

## Priority Resolution

Priority is evaluated in `GetRemoteConfigUseCase.buildConfig()`. The first enabled gate in this order wins:

```
FORCE  →  MAINTENANCE  →  MESSAGE  →  null (show HomeScreen)
```

If multiple gates are enabled simultaneously (e.g., maintenance for all + force for old versions), the highest priority one is shown. Only one dialog is ever shown at a time.

---

## UI Behaviour

- **Any gate is active:** `AppGateDialog` is shown, `HomeScreen` is hidden
- **No gate active / gate dismissed:** `HomeScreen` shown
- **Tapping outside:** Always blocked (`shouldCloseOnTouchOutSide = false`). Only button actions can dismiss.
- **Buttons:** Rendered in `FlowRow` — wrap to next line if too many. First button = filled `Button`, rest = `OutlinedButton`.

---

## Adding a New Gate Type

1. Add a new constant in `RemoteConfigKeys.kt`
2. Add the default JSON to `buildDefaults()` in `RemoteConfigDataSource.android.kt` and the stubs in `desktop`/`ios`
3. Add `fun getNewGate(): AppGateConfig` to `IRemoteConfigRepository` and implement in `RemoteConfigRepository`
4. Insert into the `listOf(...)` in `GetRemoteConfigUseCase.buildConfig()` at the correct priority position
5. Add the Firebase key in Remote Config console

---

## Testing

Run tests with:
```bash
./gradlew :composeApp:testDebugUnitTest
```

Test files:
- `AppViewModelTest` — gate loading, action handling (dismiss / URL / unknown)
- `GetRemoteConfigUseCaseTest` — priority resolution, feature flag filtering
- `RemoteConfigRepositoryTest` — JSON parsing, blank/malformed fallback, key-to-gate mapping

### Manual testing checklist

- [ ] All gates disabled → HomeScreen loads normally
- [ ] `app_gate_force` enabled with URL button → force dialog shown, button opens Play Store
- [ ] `app_gate_maintenance` enabled, no buttons → maintenance dialog, cannot dismiss by tapping outside
- [ ] `app_gate_message` enabled with dismiss button → dialog closeable
- [ ] `app_gate_force` + `app_gate_maintenance` both enabled → FORCE wins
- [ ] 4+ buttons in array → FlowRow wraps to second row
- [ ] Feature flag `feature_todo = false` → Todo tab missing from bottom nav
- [ ] Firebase unreachable on first launch → no features shown (local default = false)
