# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

MAFET is a Kotlin Multiplatform (KMP) project providing essential daily utilities. Originally an Android app, it's being actively migrated to support Android, iOS, and Desktop platforms. The project uses Clean Architecture with MVVM + MVI pattern.

**Current Features:**
- Home Dashboard (in development)
- Todo List (basic complete)
- Weather Forecast (basic complete)
- TV Shows Tracking (basic complete)
- Journal (planning phase)

## Platform Support Matrix

### Core Modules (All KMP)

| Module | Android | iOS | Desktop | Dependencies |
|--------|---------|-----|---------|--------------|
| **core-common** | ✅ | ✅ | ✅ | None - Pure Kotlin utilities |
| **core-domain** | ✅ | ✅ | ✅ | Only Coroutines - Domain result types |
| **core-data** | ✅ | ✅ | ✅ | core-common, core-domain, core-network |
| **core-network** | ✅ | ✅ | ✅ | core-common |
| **core-ui** | ✅ | ✅ | ✅ | core-common, core-data |

**Key Points:**
- **core-common**: Foundation module with utilities, extensions, logging, date/time helpers. No dependencies on other modules.
- **core-domain**: Domain layer abstractions (DomainResult, DomainError). No dependencies on other modules except coroutines.
- Module dependency order: core-common/core-domain → core-network → core-data → core-ui → features

### Feature Modules

| Module | Android | iOS | Desktop | Notes |
|--------|---------|-----|---------|-------|
| **todo** | ✅ | ✅ | ✅ | Task management - Fully migrated |
| **ai** | ✅ | ✅ | ✅ | AI features - Firebase in androidMain only |
| **journal** | ✅ | ✅ | ✅ | Note-taking - Fully migrated |
| **weather** | ✅ | ✅ | ❌ | Weather forecasts - No Desktop |
| **tv** | ✅ | ✅ | ❌ | TV tracking (TMDB) - No Desktop |
| **media** | ✅ | ✅ | ❌ | Media handling - No Desktop |
| **auth** | ✅ | ❌ | ❌ | Authentication - Android-only (Firebase) |
| **games** | ✅ | ❌ | ❌ | Gaming features - Android-only |

### Platform Applications

- **androidApp**: Android entry point with ads integration (Google Mobile Ads SDK)
- **composeApp**: Shared Compose UI aggregating all feature modules
- **desktopApp**: Desktop JVM application
- **iosApp**: iOS application (SwiftUI wrapper)

## Build & Development

### Prerequisites

**Required:** API keys must be configured in `secrets.properties` at project root:
```properties
PEXELS_API_KEY=your_key
TMDB_API_KEY=your_key
OPEN_WEATHER_FORECAST_API_KEY=your_key
OPEN_WEATHER_GEO_CODING_API_KEY=your_key
```

These keys are injected as BuildConfig fields in respective feature modules.

### Common Commands

**Build the entire project:**
```bash
./gradlew build
```

**Build Android app:**
```bash
./gradlew :androidApp:assembleDebug
./gradlew :androidApp:assembleRelease
```

**Run all tests (all platforms):**
```bash
./gradlew allTests
```

**Run platform-specific tests:**
```bash
# Android unit tests
./gradlew testDebugUnitTest

# Desktop tests
./gradlew desktopTest

# iOS tests
./gradlew iosSimulatorArm64Test
./gradlew iosX64Test

# Android instrumentation tests
./gradlew connectedDebugAndroidTest
```

**Run tests for specific module:**
```bash
./gradlew :features:todo:allTests
./gradlew :features:todo:desktopTest
./gradlew :core-ui:iosSimulatorArm64Test
./gradlew :features:weather:testDebugUnitTest
```

**Lint and code quality:**
```bash
./gradlew lint                # Run lint checks
./gradlew lintFix             # Auto-fix lint issues
./gradlew lintDebug           # Lint debug variant
./gradlew updateLintBaseline  # Update lint baseline
```

**Clean build:**
```bash
./gradlew clean
./gradlew clean build  # Clean and rebuild
```

## KMP Architecture Guidelines

### Module Dependency Rules

**CRITICAL: Dependency Order**
```
core-common (no dependencies on other modules)
       ↓
core-domain (no dependencies on other modules, only coroutines)
       ↓
core-network (depends on: core-common)
       ↓
core-data (depends on: core-common, core-domain, core-network)
       ↓
core-ui (depends on: core-common, core-data)
       ↓
Feature modules (depend on: core-common, core-ui, core-data, core-network)
```

**core-common** and **core-domain** are foundation modules:
- core-common: Pure Kotlin utilities (extensions, date/time, logging, UUID, etc.)
- core-domain: Domain result types (DomainResult, DomainError)
- Neither depends on any other project module
- All other modules can depend on these

### Source Sets Organization

KMP modules use standard source sets:
- `commonMain`: Platform-agnostic code (shared across all platforms)
- `androidMain`: Android-specific implementations
- `iosMain`: iOS-specific implementations (iosX64, iosArm64, iosSimulatorArm64)
- `desktopMain`: Desktop JVM-specific implementations
- `commonTest`: Shared test code
- `androidUnitTest`: Android-specific unit tests
- `desktopTest`: Desktop-specific tests
- `iosTest`: iOS-specific tests

### Resource Management

**CRITICAL: Use Compose Multiplatform Resources**

Resources are defined in `{module}/src/commonMain/composeResources/` and accessed via generated `Res` class. Each module generates its own Res class with a unique package.

**Configuration in build.gradle.kts:**
```kotlin
compose.resources {
    publicResClass = true
    packageOfResClass = "com.hrudhaykanth116.{module}.resources"
    generateResClass = always
}
```

**Usage:**
```kotlin
// Import module-specific resources
import com.hrudhaykanth116.todo.resources.Res
import com.hrudhaykanth116.todo.resources.*

// Drawables
Res.drawable.ic_task

// Strings with arguments
stringResource(Res.string.task_count, count)

// Use in Compose
Text(stringResource(Res.string.welcome_message))
Image(painterResource(Res.drawable.logo), contentDescription = null)
```

**DO NOT** use Android resource IDs (R.drawable, R.string) in KMP modules. Only `androidApp` module can use Android R class.

### Image Loading

Use **Coil 3.x** (KMP-compatible):

```kotlin
// For context, use LocalPlatformContext instead of Android's LocalContext
val context = LocalPlatformContext.current

AsyncImage(
    model = imageUrl,
    contentDescription = null
)
```

### Database (Room)

Room 2.7.2+ is configured for KMP across all platforms:

**In build.gradle.kts:**
```kotlin
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.androidx.room.runtime)  // KMP-compatible
        }
        androidMain.dependencies {
            implementation(libs.androidx.room.ktx)  // Android-only extensions
        }
        iosMain.dependencies {
            implementation(libs.androidx.sqlite.bundled)  // SQLite for iOS
        }
        val desktopMain by getting {
            dependencies {
                implementation(libs.androidx.sqlite.bundled)  // SQLite for Desktop
            }
        }
    }
}

// KSP for all platforms
dependencies {
    add("kspAndroid", libs.androidx.room.compiler)
    add("kspDesktop", libs.androidx.room.compiler)
    add("kspIosX64", libs.androidx.room.compiler)
    add("kspIosArm64", libs.androidx.room.compiler)
    add("kspIosSimulatorArm64", libs.androidx.room.compiler)
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
    arg("room.generateKotlin", "true")
}
```

**Key Points:**
- `room-runtime` is KMP-compatible (use in commonMain)
- `room-ktx` is Android-only (Flow extensions, suspend functions)
- Use `androidx.sqlite.bundled` for iOS and Desktop platforms

### Networking (Ktor)

Ktor 3.0.2 client is fully KMP-compatible:

**Configuration:**
```kotlin
commonMain.dependencies {
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.json)
    implementation(libs.ktor.client.logging)
}

androidMain.dependencies {
    implementation(libs.ktor.client.okhttp)  // OkHttp engine
}

val desktopMain by getting {
    dependencies {
        implementation(libs.ktor.client.okhttp)  // OkHttp for Desktop JVM
    }
}

iosMain.dependencies {
    implementation(libs.ktor.client.darwin)  // Darwin engine for iOS
}
```

### Dependency Injection (Koin)

**IMPORTANT: Using Koin 4.0.3, NOT Hilt**

The project uses Koin for DI across all platforms:

```kotlin
commonMain.dependencies {
    api(libs.koin.core)
    api(libs.koin.compose)
    api(libs.koin.compose.viewmodel)
}

androidMain.dependencies {
    implementation(libs.koin.android)
}
```

**Module structure:**
- Each module defines its own Koin module
- Use `koinViewModel()` for ViewModels in Compose
- Android-specific dependencies (e.g., Context) go in androidMain modules

### ViewModels

Use `org.jetbrains.androidx.lifecycle:lifecycle-viewmodel` version 2.8.4+ (KMP-compatible):

```kotlin
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.koin.compose.viewmodel.koinViewModel

class MyViewModel(
    private val useCase: MyUseCase,
    private val networkMonitor: NetworkMonitor,
    private val dispatcher: CoroutineDispatcher
) : UIStateViewModel<MyState, MyEvent, MyEffect>(
    initialState = UIState.Idle(MyState()),
    defaultState = MyState(),
    networkMonitor = networkMonitor
)

// In Composable
@Composable
fun MyScreen(
    viewModel: MyViewModel = koinViewModel()
) { ... }
```

**Key Points:**
- Use `androidx.lifecycle.viewModelScope` (available in commonMain)
- Inject dependencies via constructor (Koin handles DI)
- Use `koinViewModel()` in Compose instead of `viewModel()`

### UI State Management

**Pattern: UIStateViewModel with MVI Architecture**

This project uses a custom `UIStateViewModel` base class implementing MVI pattern:

```kotlin
// State: Immutable data class
data class TodoListUIState(
    val tasks: List<TodoTask> = emptyList(),
    val isLoading: Boolean = false,
    val selectedCategory: TaskCategory = TaskCategory.ALL
)

// Event: User interactions
sealed interface TodoListScreenEvent {
    data class OnTaskClick(val task: TodoTask) : TodoListScreenEvent
    data class OnCategorySelected(val category: TaskCategory) : TodoListScreenEvent
}

// Effect: One-time side effects
sealed interface TodoListEffect {
    data class NavigateToDetail(val taskId: String) : TodoListEffect
    data class ShowToast(val message: UIText) : TodoListEffect
}

// ViewModel
class TodoListViewModel(
    private val observeTasksUseCase: ObserveTasksUseCase,
    private val networkMonitor: NetworkMonitor,
    dispatcher: CoroutineDispatcher
) : UIStateViewModel<TodoListUIState, TodoListScreenEvent, TodoListEffect>(
    initialState = UIState.Idle(TodoListUIState()),
    defaultState = TodoListUIState(),
    networkMonitor = networkMonitor
) {
    override fun onEvent(event: TodoListScreenEvent) {
        when (event) {
            is TodoListScreenEvent.OnTaskClick -> {
                sendEffect(TodoListEffect.NavigateToDetail(event.task.id))
            }
        }
    }
}
```

**UIState wrapper:**
- `UIState.Idle`: Initial state
- `UIState.Loading`: Loading state
- `UIState.Success`: Success state with data
- `UIState.Error`: Error state

### Platform-Specific Code (expect/actual)

Use `expect`/`actual` pattern **sparingly** and only for true platform differences:

**Legitimate uses in this project:**
- **NetworkMonitor**: Network connectivity monitoring (uses Android ConnectivityManager, iOS Reachability, Desktop network APIs)
- **ToastManager**: Platform-specific notifications (Android Toast, iOS UIAlertController, Desktop JOptionPane)
- **Dimensions**: Screen density calculations for sdp/ssp scaling
- **Preview annotation**: Composable previews (@Preview on Android, empty on other platforms)

**Example:**
```kotlin
// In commonMain
expect class NetworkMonitor {
    fun isOnline(): Boolean
    fun observeNetworkStatus(): Flow<Boolean>
}

// In androidMain
actual class NetworkMonitor(context: Context) {
    actual fun isOnline(): Boolean = ... // Android implementation
    actual fun observeNetworkStatus(): Flow<Boolean> = ... // Android implementation
}

// In iosMain
actual class NetworkMonitor() {
    actual fun isOnline(): Boolean = ... // iOS implementation
    actual fun observeNetworkStatus(): Flow<Boolean> = ... // iOS implementation
}
```

**Avoid using expect/actual for:**
- Business logic (belongs in commonMain)
- UI components (use Compose Multiplatform)
- Data models (use @Serializable in commonMain)
- Network calls (use Ktor in commonMain)

### Platform Implementation Priority

**CRITICAL: When creating expect/actual declarations, ALWAYS implement them in this priority order:**

1. **Android (FIRST)** - Always provide a working Android implementation
2. **iOS (SECOND)** - Implement when Android is complete
3. **Desktop/Web (THIRD)** - Lowest priority

**Pattern to follow:**
```kotlin
// In commonMain
expect class PlatformFeature {
    fun doSomething(): Boolean
}

// In androidMain - MUST BE IMPLEMENTED FIRST
actual class PlatformFeature(private val context: Context) {
    actual fun doSomething(): Boolean {
        // Full Android implementation
        return true
    }
}

// In iosMain - Can be TODO initially
actual class PlatformFeature() {
    actual fun doSomething(): Boolean {
        // TODO: iOS implementation
        return false
    }
}

// In desktopMain - Can be TODO initially
actual class PlatformFeature() {
    actual fun doSomething(): Boolean {
        // TODO: Desktop implementation
        return false
    }
}
```

**Dependency Injection Pattern:**
```kotlin
// In commonMain/di/
expect val platformModule: Module

val mainModule = module {
    includes(platformModule)
    // Common dependencies
}

// In androidMain/di/
actual val platformModule = module {
    single<PlatformFeature> {
        PlatformFeature(androidContext())
    }
}

// In iosMain/di/
actual val platformModule = module {
    single<PlatformFeature> {
        PlatformFeature()
    }
}
```

**Rationale:**
- Android is the primary development platform
- Most users are on Android
- iOS requires different tooling and longer build times
- Desktop is lowest priority for mobile-first features

## Testing

### Test Structure

KMP modules support testing across all platforms:

```
src/
  commonTest/kotlin/        # Shared tests (run on all platforms)
  androidUnitTest/java/     # Android-specific unit tests
  desktopTest/kotlin/       # Desktop-specific tests
  iosTest/kotlin/           # iOS-specific tests
  androidTest/java/         # Android instrumentation tests
```

### Testing Libraries

**Main testing dependencies:**
```kotlin
commonTest.dependencies {
    implementation(kotlin("test"))
    implementation(libs.kotlinx.coroutines.test)
}

val androidUnitTest by getting {
    dependencies {
        implementation(libs.junit)        // JUnit 4.13.2
        implementation(libs.mockk)        // MockK 1.14.5
    }
}
```

**IMPORTANT: Use MockK, not Mockito**
- MockK is Kotlin-first and works better with coroutines
- Supports extension functions, suspend functions, and Kotlin features

**Example test:**
```kotlin
// In commonTest
class TodoRepositoryTest {
    @Test
    fun `test getTasks returns success`() = runTest {
        // Test implementation
    }
}
```

### Running Tests

```bash
# All tests across all platforms
./gradlew allTests

# Specific platform
./gradlew desktopTest
./gradlew testDebugUnitTest
./gradlew iosSimulatorArm64Test

# Specific module
./gradlew :features:todo:allTests
```

## Dependency Versions

Managed via `gradle/libs.versions.toml`:

**Core:**
- Kotlin: 2.2.0
- Kotlin Compiler Extension: 1.5.15
- KSP: 2.2.0-2.0.2
- Java Version: 17

**Android:**
- Min SDK: 24
- Target SDK: 36
- Compile SDK: 36
- Android Gradle Plugin: 8.11.0

**Compose:**
- JetBrains Compose: 1.10.1
- Compose BOM: 2024.12.01
- Coil: 3.0.4 (KMP image loading)

**KMP Libraries:**
- Ktor: 3.0.2
- Koin: 4.0.3
- Room: 2.7.2
- Coroutines: 1.10.2
- Kotlinx Serialization: (from Kotlin)
- Kotlinx DateTime: 0.7.1
- Kotlinx Immutable Collections: 0.4.0
- Kermit Logger: 2.0.4

**Android-Specific:**
- AndroidX Core: 1.16.0
- AndroidX Lifecycle: 2.9.1
- Navigation Compose: 2.9.2
- JetBrains Lifecycle: 2.8.4
- Paging: 3.3.6
- Media3 ExoPlayer: 1.7.1 (in core-ui androidMain)
- Firebase BOM: 33.16.0 (in auth, ai modules)
- Play Services Ads: 24.9.0 (in androidApp)
- Play Services Location: 21.3.0 (in weather module)

## Migration Guide (Android → KMP)

### Migration Status

**✅ Fully Migrated to KMP:**
- Core modules: core-common, core-domain, core-data, core-network, core-ui
- Features: todo (Android + iOS + Desktop), ai (Android + iOS + Desktop), journal (Android + iOS + Desktop)
- Features: weather, tv, media (Android + iOS only, no Desktop yet)

**❌ Still Android-Only:**
- `auth` module (uses Firebase Authentication)
- `games` module
- Ads integration in `androidApp` (Google Mobile Ads SDK)

### Migration Checklist

When migrating an Android module to KMP:

**1. Update build.gradle.kts**
```kotlin
// Replace
plugins {
    id("com.android.library")
    alias(libs.plugins.kotlin.android)
}

// With
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose)
}

kotlin {
    androidTarget { ... }
    jvm("desktop") { ... }  // If supporting desktop
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies { ... }
        androidMain.dependencies { ... }
        iosMain.dependencies { ... }
    }
}
```

**2. Restructure source sets**
```
Before:
  src/main/java/

After:
  src/
    commonMain/kotlin/      # Shared code
    androidMain/kotlin/     # Android-specific
    iosMain/kotlin/         # iOS-specific
    desktopMain/kotlin/     # Desktop-specific
```

**3. Replace Android-specific dependencies**
- ❌ Android R class → ✅ Compose Resources (Res.string, Res.drawable)
- ❌ Android Context → ✅ `coil3.compose.LocalPlatformContext`
- ❌ Hilt DI → ✅ Koin
- ❌ Android ViewModel → ✅ `org.jetbrains.androidx.lifecycle.ViewModel`
- ❌ Android Room KTX → ✅ Room runtime in commonMain, KTX in androidMain only
- ❌ Retrofit → ✅ Ktor Client
- ❌ Gson/Moshi → ✅ kotlinx.serialization

**4. Move resources to Compose Resources**
```
Before: res/drawable/*, res/values/strings.xml

After: src/commonMain/composeResources/
  drawable/
    ic_logo.xml
  values/
    strings.xml
```

Configure in build.gradle.kts:
```kotlin
compose.resources {
    publicResClass = true
    packageOfResClass = "com.hrudhaykanth116.{module}.resources"
    generateResClass = always
}
```

**5. Handle platform-specific APIs**

Move Android-specific code to `androidMain`:
```kotlin
// In commonMain - expect declaration
expect class LocationManager() {
    fun getCurrentLocation(): Flow<Location?>
}

// In androidMain - actual implementation
actual class LocationManager(private val context: Context) {
    actual fun getCurrentLocation(): Flow<Location?> {
        // Android implementation using FusedLocationProviderClient
    }
}

// In iosMain - actual implementation
actual class LocationManager() {
    actual fun getCurrentLocation(): Flow<Location?> {
        // iOS implementation using CLLocationManager
    }
}
```

**6. Update imports**
```kotlin
// Replace Android imports
❌ import android.content.Context
❌ import androidx.compose.runtime.R
❌ import com.hrudhaykanth116.core.R as CoreR

// With KMP imports
✅ import coil3.compose.LocalPlatformContext
✅ import mafet.core_ui.generated.resources.Res
✅ import com.hrudhaykanth116.{module}.resources.Res
```

**7. Testing**
- Move common tests to `commonTest`
- Keep Android-specific tests in `androidUnitTest`
- Add platform-specific tests as needed

### Common Migration Patterns

**Pattern 1: Context Usage**
```kotlin
// Before (Android)
@Composable
fun MyComposable() {
    val context = LocalContext.current
    AsyncImage(model = url, contentDescription = null)
}

// After (KMP)
@Composable
fun MyComposable() {
    val context = LocalPlatformContext.current  // Coil3
    AsyncImage(model = url, contentDescription = null)
}
```

**Pattern 2: String Resources**
```kotlin
// Before (Android)
UIText.StringResource(R.string.welcome_message)

// After (KMP)
UIText.StringResource(Res.string.welcome_message)
```

**Pattern 3: Room Database**
```kotlin
// commonMain
@Database(entities = [Task::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}

// androidMain - create database
fun getDatabaseBuilder(context: Context): RoomDatabase.Builder<AppDatabase> {
    return Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        "app_database"
    )
}

// iosMain - create database
fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val dbFile = "${NSHomeDirectory()}/app_database.db"
    return Room.databaseBuilder<AppDatabase>(
        name = dbFile,
    )
}
```

### Modules Not Requiring Migration

Keep these Android-only when they use Android-specific SDKs:
- **auth**: Uses Firebase Auth (Android SDK)
- **games**: Uses Android-specific game libraries
- **androidApp**: Entry point with Google Mobile Ads

## Architecture Patterns

### Architecture Overview Diagram

```
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│                                    MAFET Architecture                                    │
├─────────────────────────────────────────────────────────────────────────────────────────┤
│                                                                                         │
│  ┌─────────────────────────────────────────────────────────────────────────────────┐   │
│  │                           Platform Entry Points                                  │   │
│  │  ┌───────────────┐   ┌───────────────┐   ┌───────────────┐   ┌──────────────┐  │   │
│  │  │  androidApp   │   │  desktopApp   │   │    iosApp     │   │  composeApp  │  │   │
│  │  │  (Activity)   │   │   (main())    │   │  (SwiftUI)    │   │  (Shared UI) │  │   │
│  │  └───────┬───────┘   └───────┬───────┘   └───────┬───────┘   └──────┬───────┘  │   │
│  │          │                   │                   │                  │          │   │
│  │          └───────────────────┴───────────────────┴──────────────────┘          │   │
│  │                                        │                                        │   │
│  └────────────────────────────────────────┼────────────────────────────────────────┘   │
│                                           │                                             │
│  ┌────────────────────────────────────────┼────────────────────────────────────────┐   │
│  │                              Feature Modules                                     │   │
│  │  ┌──────────┐ ┌──────────┐ ┌──────────┐ ┌──────────┐ ┌──────────┐ ┌──────────┐ │   │
│  │  │   todo   │ │    tv    │ │ weather  │ │ journal  │ │    ai    │ │  media   │ │   │
│  │  │ A+I+D ✓  │ │  A+I ✓   │ │  A+I ✓   │ │ A+I+D ✓  │ │ A+I+D ✓  │ │ A+I+D ✓  │ │   │
│  │  └────┬─────┘ └────┬─────┘ └────┬─────┘ └────┬─────┘ └────┬─────┘ └────┬─────┘ │   │
│  │       │            │            │            │            │            │       │   │
│  │  ┌──────────┐ ┌──────────┐      │            │            │            │       │   │
│  │  │   auth   │ │  games   │      │            │            │            │       │   │
│  │  │ A only   │ │ A only   │      │            │            │            │       │   │
│  │  └────┬─────┘ └────┬─────┘      │            │            │            │       │   │
│  │       └────────────┴────────────┴────────────┴────────────┴────────────┘       │   │
│  └─────────────────────────────────────┬───────────────────────────────────────────┘   │
│                                        │                                                │
│  ┌─────────────────────────────────────┼───────────────────────────────────────────┐   │
│  │                                core-ui                                           │   │
│  │              (Components, Theme, UIStateViewModel, NetworkMonitor)               │   │
│  └─────────────────────────────────────┬───────────────────────────────────────────┘   │
│                                        │                                                │
│  ┌────────────────┬────────────────────┴────────────────────┬──────────────────────┐   │
│  │                │                                          │                      │   │
│  │  ┌─────────────┴─────────────┐  ┌─────────────────────────┴─────────────────┐   │   │
│  │  │        core-data          │  │               core-network                 │   │   │
│  │  │  (Repositories, Mappers)  │  │         (Ktor Client, API Config)          │   │   │
│  │  └─────────────┬─────────────┘  └─────────────────────────┬─────────────────┘   │   │
│  │                │                                          │                      │   │
│  │  ┌─────────────┴──────────────────────────────────────────┴─────────────────┐   │   │
│  │  │                           core-domain                                     │   │   │
│  │  │                    (DomainResult, DomainError)                            │   │   │
│  │  └─────────────────────────────────┬────────────────────────────────────────┘   │   │
│  │                                    │                                             │   │
│  │  ┌─────────────────────────────────┴────────────────────────────────────────┐   │   │
│  │  │                           core-common                                     │   │   │
│  │  │              (Extensions, Utils, DateTime, Logging, UUID)                 │   │   │
│  │  └──────────────────────────────────────────────────────────────────────────┘   │   │
│  └──────────────────────────────────────────────────────────────────────────────────┘   │
│                                                                                         │
│  Legend: A = Android, I = iOS, D = Desktop, ✓ = KMP Supported                          │
└─────────────────────────────────────────────────────────────────────────────────────────┘
```

### UI State Management (MVI Pattern) Diagram

```
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│                              MVI Pattern - UIStateViewModel                              │
├─────────────────────────────────────────────────────────────────────────────────────────┤
│                                                                                         │
│   ┌─────────────────────┐          Events (user actions)          ┌─────────────────┐  │
│   │                     │ ─────────────────────────────────────► │                 │  │
│   │      UI Screen      │                                         │   ViewModel     │  │
│   │     (Composable)    │ ◄───────────────────────────────────── │(UIStateViewModel│  │
│   │                     │           UIState<T> (State)            │                 │  │
│   └──────────┬──────────┘                                         └────────┬────────┘  │
│              │                                                             │           │
│              │                         Effects                             │           │
│              │ ◄───────────────────────────────────────────────────────────┘           │
│              │              (Navigation, Toast, One-time events)                       │
│              │                                                                         │
│   ┌──────────┴──────────────────────────────────────────────────────────────────────┐  │
│   │                                                                                  │  │
│   │   ┌──────────────────────────────────────────────────────────────────────────┐  │  │
│   │   │                           UIState<T> Sealed Class                         │  │  │
│   │   │  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────────────┐   │  │  │
│   │   │  │  UIState.Idle   │  │ UIState.Loading │  │    UIState.Error        │   │  │  │
│   │   │  │  contentState?  │  │  contentState?  │  │ DomainError + content?  │   │  │  │
│   │   │  │  userMessage?   │  │    message?     │  │                         │   │  │  │
│   │   │  └─────────────────┘  └─────────────────┘  └─────────────────────────┘   │  │  │
│   │   └──────────────────────────────────────────────────────────────────────────┘  │  │
│   │                                                                                  │  │
│   └──────────────────────────────────────────────────────────────────────────────────┘  │
│                                                                                         │
│   Data Flow:                                                                            │
│   ┌────────────────────────────────────────────────────────────────────────────────┐   │
│   │  1. User interacts with UI (click, input)                                       │   │
│   │  2. UI sends Event to ViewModel (processEvent())                                │   │
│   │  3. ViewModel processes event and calls Use Cases                               │   │
│   │  4. Use Cases interact with Repository                                          │   │
│   │  5. ViewModel updates UIState (setState())                                      │   │
│   │  6. UI observes StateFlow and recomposes                                        │   │
│   │  7. For navigation/toast, ViewModel sends Effect (setEffect())                  │   │
│   │  8. UI collects Effect from SharedFlow and handles one-time action              │   │
│   └────────────────────────────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────────────────────────────┘
```

### Feature Module Structure

```
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│                            Feature Module Structure (e.g., todo)                         │
├─────────────────────────────────────────────────────────────────────────────────────────┤
│                                                                                         │
│  features/todo/                                                                         │
│  ├── src/                                                                               │
│  │   ├── commonMain/kotlin/com/hrudhaykanth116/todo/                                   │
│  │   │   ├── data/                         # Data Layer                                │
│  │   │   │   ├── data_source/local/        # Room DAOs and local data sources          │
│  │   │   │   ├── local/room/               # Database, Entities, DAOs                  │
│  │   │   │   ├── mappers/                  # Entity ↔ Domain mappers                   │
│  │   │   │   └── repositories/             # Repository implementations                │
│  │   │   │                                                                              │
│  │   │   ├── domain/                       # Domain Layer                              │
│  │   │   │   ├── model/                    # Domain models (TodoModel, TaskCategory)   │
│  │   │   │   ├── repository/               # Repository interfaces                     │
│  │   │   │   └── use_cases/                # Business logic use cases                  │
│  │   │   │                                                                              │
│  │   │   ├── ui/                           # Presentation Layer                        │
│  │   │   │   ├── components/               # Reusable UI components                    │
│  │   │   │   ├── mappers/                  # Domain → UI model mappers                 │
│  │   │   │   ├── models/                   # UI states, events, effects               │
│  │   │   │   └── screens/                  # Screen composables and ViewModels         │
│  │   │   │                                                                              │
│  │   │   ├── di/                           # Koin module definition                    │
│  │   │   └── navigation/                   # Navigation routes                          │
│  │   │                                                                                  │
│  │   ├── androidMain/kotlin/               # Android-specific implementations          │
│  │   │   └── TodoDatabaseBuilder.android.kt                                            │
│  │   │                                                                                  │
│  │   ├── iosMain/kotlin/                   # iOS-specific implementations              │
│  │   │   └── TodoDatabaseBuilder.ios.kt                                                │
│  │   │                                                                                  │
│  │   ├── desktopMain/kotlin/               # Desktop-specific implementations          │
│  │   │   └── TodoDatabaseBuilder.desktop.kt                                            │
│  │   │                                                                                  │
│  │   └── commonTest/kotlin/                # Shared tests                              │
│  │                                                                                      │
│  └── build.gradle.kts                      # KMP module configuration                  │
└─────────────────────────────────────────────────────────────────────────────────────────┘
```

### Shared UI Components (core-ui)

```
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│                              core-ui Shared Components                                   │
├─────────────────────────────────────────────────────────────────────────────────────────┤
│                                                                                         │
│  ┌─────────────────────────────────────────────────────────────────────────────────┐   │
│  │                              UI Components                                       │   │
│  │  AppCard, AppCircularImage, AppClickableIcon, AppDatePicker, AppDateTimePicker  │   │
│  │  AppDialog, AppDropDown, AppFormButton, AppIcon, AppImage, AppInputText         │   │
│  │  AppProgressBar, AppRoundedIcon, AppScreen, AppSearchBar, AppSlider, AppText    │   │
│  │  AppToolbar, AppToolBarIcon, CenteredColumn, ExpandableView, FancyChip          │   │
│  │  Flippable, HorizontalMonthDates, HorizontalSpacer, VerticalSpacer              │   │
│  │  TooltipWithTriangle, VideoPlayerScreen                                          │   │
│  └─────────────────────────────────────────────────────────────────────────────────┘   │
│                                                                                         │
│  ┌─────────────────────────────────────────────────────────────────────────────────┐   │
│  │                            UI Models (models/)                                   │   │
│  │  UIState (Loading, Idle, Error) - Core state wrapper                            │   │
│  │  UIText (StringValue, StringResource) - Platform-agnostic text                  │   │
│  │  UserMessage - Snackbar/Toast messages                                          │   │
│  └─────────────────────────────────────────────────────────────────────────────────┘   │
│                                                                                         │
│  ┌─────────────────────────────────────────────────────────────────────────────────┐   │
│  │                         ViewModels (viewmodels/)                                 │   │
│  │  UIStateViewModel<STATE, EVENT, EFFECT>                                         │   │
│  │  - uiStateFlow: StateFlow<UIState<STATE>>                                       │   │
│  │  - effect: SharedFlow<EFFECT>                                                   │   │
│  │  - processEvent(EVENT)                                                          │   │
│  │  - setState(), setLoadingState(), setIdleState(), setEffect()                   │   │
│  └─────────────────────────────────────────────────────────────────────────────────┘   │
│                                                                                         │
│  ┌─────────────────────────────────────────────────────────────────────────────────┐   │
│  │                    Platform Abstractions (expect/actual)                         │   │
│  │  NetworkMonitor - Network connectivity monitoring                               │   │
│  │  ToastManager - Platform-specific notifications                                 │   │
│  │  Dimensions - Screen density calculations                                       │   │
│  │  Preview - @Preview annotation wrapper                                          │   │
│  └─────────────────────────────────────────────────────────────────────────────────┘   │
│                                                                                         │
│  ┌─────────────────────────────────────────────────────────────────────────────────┐   │
│  │                              Theme (theme/)                                      │   │
│  │  Color scheme, Typography, AppTheme composable                                  │   │
│  └─────────────────────────────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────────────────────────────┘
```

### Clean Architecture Layers

The project follows Clean Architecture with clear separation of concerns:

**1. Domain Layer (core-domain)**
- Pure Kotlin business logic
- Domain models
- Result types: `DomainResult<T>`, `DomainError`
- No dependencies on other layers

**2. Data Layer (core-data)**
- Repository implementations
- Room database DAOs and entities
- Data source abstractions
- Maps domain models to/from data models

**3. Presentation Layer (core-ui, features)**
- ViewModels (UIStateViewModel with MVI)
- UI components (Composables)
- UI models/states
- UI mappers (domain → UI models)

### MVVM + MVI Pattern

**State Management Flow:**
```
User Interaction → Event → ViewModel → State → UI
                            ↓
                        Side Effect → Navigation/Toast
```

**Example:**
```kotlin
// User clicks button
onClick = { viewModel.onEvent(TaskEvent.OnDeleteClick(task)) }

// ViewModel processes event
override fun onEvent(event: TaskEvent) {
    when (event) {
        is TaskEvent.OnDeleteClick -> {
            viewModelScope.launch {
                deleteTaskUseCase(event.task.id)
                    .onSuccess { sendEffect(TaskEffect.ShowToast("Deleted")) }
                    .onError { updateState { copy(error = it) } }
            }
        }
    }
}

// UI observes state
val state by viewModel.state.collectAsState()
```

### Repository Pattern

Repositories handle data operations and provide clean API to ViewModels:

```kotlin
interface TodoRepository {
    fun observeTasks(): Flow<List<TodoModel>>
    suspend fun createTask(task: TodoModel): DomainResult<Unit>
    suspend fun deleteTask(id: String): DomainResult<Unit>
}

class TodoRepositoryImpl(
    private val dao: TodoDao,
    private val dispatcher: CoroutineDispatcher
) : TodoRepository {
    override fun observeTasks(): Flow<List<TodoModel>> =
        dao.observeAllTasks()
            .map { entities -> entities.map { it.toDomainModel() } }
            .flowOn(dispatcher)
}
```

### Use Case Pattern

Each feature has focused use cases for specific operations:

```kotlin
class CreateTodoTaskUseCase(
    private val repository: TodoRepository
) {
    suspend operator fun invoke(task: TodoModel): DomainResult<Unit> {
        return repository.createTask(task)
    }
}
```

## Code Style & Conventions

### Package Structure
```
com.hrudhaykanth116.{module}.{layer}

Examples:
com.hrudhaykanth116.todo.domain.model
com.hrudhaykanth116.todo.domain.use_cases
com.hrudhaykanth116.todo.data.repository
com.hrudhaykanth116.todo.ui.screens.list
com.hrudhaykanth116.todo.ui.models
```

### Naming Conventions

**ViewModels:**
- Pattern: `{Screen}ViewModel`
- Example: `TodoListViewModel`, `TaskDetailViewModel`

**Use Cases:**
- Pattern: `{Action}{Entity}UseCase`
- Example: `CreateTodoTaskUseCase`, `ObserveTasksUseCase`

**Repositories:**
- Interface: `{Entity}Repository`
- Implementation: `{Entity}RepositoryImpl`

**UI States:**
- Pattern: `{Screen}UIState`, `{Screen}Event`, `{Screen}Effect`
- Example: `TodoListUIState`, `TodoListScreenEvent`, `TodoListEffect`

### Kotlin Best Practices

**Immutability:**
```kotlin
// Use immutable collections
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class TodoListUIState(
    val tasks: ImmutableList<TodoTask> = persistentListOf(),
    val isLoading: Boolean = false
)
```

**Coroutines:**
```kotlin
// Inject dispatcher for testability
class MyViewModel(
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    init {
        viewModelScope.launch(dispatcher) {
            // Coroutine work
        }
    }
}
```

**Serialization:**
```kotlin
@Serializable
data class TodoModel(
    val id: String,
    val title: String,
    @SerialName("created_at")
    val createdAt: Long
)
```

### Compose Guidelines

**Material3:**
- Use Material3 components throughout
- Follow Material3 design guidelines
- Use Material3 color scheme and typography

**Composable structure:**
```kotlin
@Composable
fun MyScreen(
    viewModel: MyViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    MyScreenContent(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun MyScreenContent(
    state: MyUIState,
    onEvent: (MyEvent) -> Unit
) {
    // UI implementation
}
```

**Preview annotations:**
```kotlin
// In commonMain (for KMP modules)
@Preview
@Composable
fun MyComponentPreview() {
    MyComponent()
}
```

## Project-Specific Notes

### Single Activity Architecture

Android app uses single-activity architecture:
- Entry point: `MainActivity` in `androidApp` module
- Navigation: Jetpack Navigation Compose with type-safe routes
- No Fragment usage

### API Configuration

API keys are injected via BuildConfig:
- Defined in `secrets.properties`
- Read in root `build.gradle.kts`
- Injected into feature modules as BuildConfig fields
- Example: `BuildConfig.TMDB_API_KEY` in tv module

### Resource Naming

Follow naming conventions for Compose Resources:
- Drawables: `ic_{name}`, `img_{name}`, `bg_{name}`
- Strings: `{module}_{screen}_{purpose}` (e.g., `todo_list_empty_message`)
- Keep resource names lowercase with underscores

### Logging

Use Kermit logger (KMP-compatible):
```kotlin
import co.touchlab.kermit.Logger

Logger.d { "Debug message" }
Logger.e { "Error message" }
```

## Feature Details

### Todo Module
**Status**: Production Ready | **Platforms**: Android, iOS, Desktop

A comprehensive task management system:
- Create, update, delete tasks with rich metadata
- Category filtering (Work, Personal, Shopping, Health, Finance, etc.)
- Priority levels (HIGH, MEDIUM, LOW) with visual indicators
- Due date/time tracking
- Search and sort functionality (by date, priority, title)
- Sync status tracking for offline support
- Shared element transitions for smooth navigation

**Key Files**:
- `TodoListViewModel`: `features/todo/src/commonMain/kotlin/.../ui/screens/list/TodoListViewModel.kt`
- `TodoModel`: `features/todo/src/commonMain/kotlin/.../domain/model/TodoModel.kt`
- `Room Database`: `features/todo/src/commonMain/kotlin/.../data/local/room/dbs/TodoDb.kt`

### TV Shows Module
**Status**: Production Ready | **Platforms**: Android, iOS (No Desktop yet)

TV show tracking powered by TMDB API:
- Home screen with categorized shows (Trending, Popular, Top Rated, Airing Today)
- Search functionality with instant results
- Detailed show information (cast, seasons, episodes, ratings)
- Personal watchlist with watch status tracking (Plan to Watch, Watching, Completed, Dropped)
- Similar shows recommendations

**Key Files**:
- `EntertainmentHomeScreen`: `features/tv/src/commonMain/kotlin/ui/screens/home/EntertainmentHomeScreen.kt`
- `TvDetailsScreen`: `features/tv/src/commonMain/kotlin/ui/screens/details/TvDetailsScreen.kt`
- `TMDB API Service`: `features/tv/src/commonMain/kotlin/data/datasources/remote/ktor/TmdbApiServiceKtor.kt`

### Weather Module
**Status**: Production Ready | **Platforms**: Android, iOS (No Desktop yet)

Real-time weather information with OpenWeather API:
- Current conditions (temperature, humidity, wind speed, visibility)
- Hourly forecast with weather icons
- Daily forecast for upcoming days
- Location-based weather using GPS
- Search by city with geocoding
- DataStore for caching last known location

**Key Files**:
- `WeatherHomeScreen`: `features/weather/src/commonMain/kotlin/.../ui/screens/home/WeatherHomeScreen.kt`
- `LocationService`: `features/weather/src/commonMain/kotlin/.../location/LocationService.kt` (expect/actual)
- `OpenWeather API`: `features/weather/src/commonMain/kotlin/.../data/datasources/remote/ktor/OpenWeatherApiServiceKtor.kt`

### Journal Module
**Status**: Development | **Platforms**: Android, iOS, Desktop

Personal note-taking with emotional awareness:
- Create and manage journal entries
- Emotion slider for mood tracking
- Rich text content
- Date-based organization
- Full offline support with Room

**Key Files**:
- `JournalListScreen`: `features/journal/src/commonMain/kotlin/.../ui/screens/list/JournalListScreen.kt`
- `JournalEntry`: `features/journal/src/commonMain/kotlin/.../domain/model/JournalEntry.kt`

### AI Module
**Status**: Development | **Platforms**: Android (Full), iOS/Desktop (Partial)

AI-powered query system:
- Natural language query interface
- Firebase Vertex AI integration (Android only)
- Platform-specific implementations via expect/actual pattern

**Key Files**:
- `AIScreen`: `features/ai/src/commonMain/kotlin/.../AIScreen.kt` (expect)
- `QueryScreen`: `features/ai/src/androidMain/kotlin/.../ui/screens/query/QueryScreen.kt` (Android impl)

### Media Module
**Status**: Development | **Platforms**: Android, iOS, Desktop

Explore photos and videos from Pexels API:
- Curated content feed
- Search with filters (orientation, color, size)
- Staggered grid layout
- Detail view with color palette extraction

**Key Files**:
- `MediaHomeScreen`: `features/media/src/commonMain/kotlin/.../ui/screens/home/MediaHomeScreen.kt`
- `PexelsApiService`: `features/media/src/commonMain/kotlin/.../data/network/ktor/PexelsApiServiceKtor.kt`

### Auth Module
**Status**: Complete | **Platforms**: Android Only

Firebase-powered authentication:
- Email/password login and signup
- Form validation with real-time feedback
- User profile management

**Note**: Not migrated to KMP due to Firebase Android SDK dependency.

### Games Module
**Status**: Experimental | **Platforms**: Android Only

Sprite-based game experiments:
- Custom sprite animation system
- Touch gesture detection
- Game state management

**Note**: Not planned for KMP migration.

## Roadmap

### Current Sprint
- [ ] Desktop support for TV Shows module
- [ ] Desktop support for Weather module
- [ ] WorkManager background sync for Todo
- [ ] Local notifications for task reminders

### Planned
- [ ] Widget support for Android (Todo, Weather)
- [ ] Offline-first sync architecture
- [ ] iOS native integrations (HealthKit for Journal emotions)
- [ ] End-to-end encryption for Journal entries

### Future Ideas
- [ ] AI-powered task suggestions
- [ ] Social features for TV tracking
- [ ] Weather alerts and notifications
- [ ] Watch OS companion app
- [ ] Web support (Compose for Web)

## External APIs

| API | Module | Documentation |
|-----|--------|---------------|
| OpenWeather | weather | https://openweathermap.org/api |
| TMDB | tv | https://www.themoviedb.org/documentation/api |
| Pexels | media | https://www.pexels.com/api/documentation/ |
| Firebase | ai, auth | https://firebase.google.com/docs |
