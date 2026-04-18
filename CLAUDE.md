# CLAUDE.md

MAFET is a Kotlin Multiplatform (KMP) app (Android + iOS + Desktop) using Clean Architecture + MVVM/MVI.
See [README.md](README.md) for features, platform matrix, tech stack, and project structure.

---

## Build Commands

```bash
./gradlew build                          # Full build
./gradlew :androidApp:assembleDebug      # Android debug APK
./gradlew allTests                       # All tests (all platforms)
./gradlew testDebugUnitTest              # Android unit tests
./gradlew desktopTest                    # Desktop tests
./gradlew iosSimulatorArm64Test          # iOS tests
./gradlew :features:todo:allTests        # Module-specific tests
./gradlew lint / lintFix                 # Lint
./gradlew clean build                    # Clean rebuild
```
---

## Critical Rules

1. **No Android APIs in commonMain** — no `android.content.Context`, no `R.string`, no `LocalContext`.
2. **Use Compose Resources** (`Res.drawable.*`, `Res.string.*`) in all KMP modules. Only `androidApp` may use the Android R class.
3. **Use Koin** for DI — NOT Hilt. Use `koinViewModel()` in Compose.
4. **Use MockK** for mocking in tests — NOT Mockito.
7. **expect/actual sparingly** — only for true platform differences (NetworkMonitor, ToastManager, Dimensions, @Preview). Not for business logic, UI, or network.
8. **Platform implementation priority:** Android first → iOS second → Desktop last(Only if asked for).
9. **`room-ktx` is Android-only** — put in `androidMain`. Use `room-runtime` in `commonMain`.
10. **Inject `CoroutineDispatcher`** into ViewModels for testability.
11. **Platform checks** — use `currentPlatform` / `isAndroid` / `isIos` / `isDesktop` from `com.hrudhaykanth116.core.common.platform`. Do NOT add new per-module `expect val isX: Boolean` flags.
12. **API keys** — all secrets live in `com.hrudhaykanth116.core.common.config.ApiConfig` (core-common). Do NOT create per-module `XxxApiConfig` or per-module `buildConfigField`/`generateDesktopBuildConfig` plumbing. See [docs/kmp-guide.md](docs/kmp-guide.md#api-keys--secrets) for adding a new key.

---

## Key Libraries

Full versions: `gradle/libs.versions.toml`

---

## Docs

- [docs/architecture.md](docs/architecture.md) — MVI pattern, UIStateViewModel, feature module structure, repository pattern
- [docs/kmp-guide.md](docs/kmp-guide.md) — Room/Ktor/Koin setup, Compose Resources, expect/actual, migration checklist
- [docs/conventions.md](docs/conventions.md) — Naming conventions, package structure, Compose guidelines
