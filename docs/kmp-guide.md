# KMP Implementation Guide

## Source Sets

```
commonMain/     # Shared code (all platforms)
androidMain/    # Android-specific
iosMain/        # iOS (iosX64, iosArm64, iosSimulatorArm64)
desktopMain/    # Desktop JVM
commonTest/     # Shared tests
androidUnitTest/
desktopTest/
iosTest/
```

---

## Compose Resources

Resources live in `{module}/src/commonMain/composeResources/`. Each module generates its own `Res` object.

**build.gradle.kts:**
```kotlin
compose.resources {
    publicResClass = true
    packageOfResClass = "com.hrudhaykanth116.{module}.resources"
    generateResClass = always
}
```

**Imports** — use the generated package (not the configured one):
```kotlin
import mafet.core_ui.generated.resources.Res
import mafet.core_ui.generated.resources.ic_account

// Feature module example
import mafet.todo.generated.resources.Res
import mafet.todo.generated.resources.ic_task
```

Pattern: `mafet.{module_name}.generated.resources.Res`

**Usage:**
```kotlin
Res.drawable.ic_task
UIText.StringRes(Res.string.todo_error_generic)
painterResource(Res.drawable.ic_task)
```

**Never** use `R.drawable` / `R.string` in KMP modules — only `androidApp` may use the Android R class.

Resource naming: `ic_{name}`, `img_{name}`, `bg_{name}` for drawables; `{module}_{screen}_{purpose}` for strings.

---

## Room (KMP)

```kotlin
// commonMain — KMP-compatible runtime
implementation(libs.androidx.room.runtime)

// androidMain only — Flow/suspend extensions
implementation(libs.androidx.room.ktx)

// iOS + Desktop — bundled SQLite
implementation(libs.androidx.sqlite.bundled)

// KSP for all platforms
add("kspAndroid", libs.androidx.room.compiler)
add("kspDesktop", libs.androidx.room.compiler)
add("kspIosX64", libs.androidx.room.compiler)
add("kspIosArm64", libs.androidx.room.compiler)
add("kspIosSimulatorArm64", libs.androidx.room.compiler)

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
    arg("room.generateKotlin", "true")
}
```

**Database builder — top-level expect/actual function:**
```kotlin
// commonMain
expect fun getDatabaseBuilder(): RoomDatabase.Builder<TodoDb>

// androidMain — gets Context from Koin
actual fun getDatabaseBuilder(): RoomDatabase.Builder<TodoDb> {
    val context = getKoin().get<android.content.Context>()
    return Room.databaseBuilder(
        context.applicationContext, TodoDb::class.java, TodoDb.TABLE_NAME
    ).fallbackToDestructiveMigration(dropAllTables = true)
}

// iosMain
actual fun getDatabaseBuilder(): RoomDatabase.Builder<TodoDb> {
    val dbFile = "${NSHomeDirectory()}/todo.db"
    return Room.databaseBuilder<TodoDb>(name = dbFile)
}
```

**In DI module:**
```kotlin
single<TodoDb> { getDatabaseBuilder().build() }
```

---

## Ktor (KMP)

```kotlin
// commonMain
implementation(libs.ktor.client.core)
implementation(libs.ktor.client.content.negotiation)
implementation(libs.ktor.serialization.json)
implementation(libs.ktor.client.logging)

// androidMain + desktopMain
implementation(libs.ktor.client.okhttp)

// iosMain
implementation(libs.ktor.client.darwin)
```

---

## Koin (DI)

**Use Koin 4.0.3 — NOT Hilt.**

```kotlin
// commonMain
api(libs.koin.core)
api(libs.koin.compose)
api(libs.koin.compose.viewmodel)

// androidMain
implementation(libs.koin.android)
```

- Use `koinViewModel()` in Compose (not `viewModel()`)
- Use `koinViewModel { parametersOf(arg) }` when passing arguments
- Named qualifiers: `get(named(DispatchersEnum.IoDispatcher))`
- Android Context in DI: `androidContext()` or `getKoin().get<Context>()`

**expect/actual DI pattern:**
```kotlin
// commonMain/di/
expect val platformModule: Module
val mainModule = module { includes(platformModule) }

// androidMain/di/
actual val platformModule = module {
    single { MyFeature(androidContext()) }
}

// iosMain/di/
actual val platformModule = module {
    single { MyFeature() }
}
```

---

## Image Loading

Use **Coil 3.x** (KMP-compatible). Use `LocalPlatformContext` instead of `LocalContext`:
```kotlin
val context = LocalPlatformContext.current
AsyncImage(model = imageUrl, contentDescription = null)
```

---

## expect/actual Pattern

Use **sparingly** — only for true platform differences:
- `NetworkMonitor` — connectivity APIs differ per platform
- `Dimensions` — screen density calculations

**Avoid** for: business logic, UI components, data models, network calls.

**Implementation priority: Android → iOS → Desktop**

```kotlin
// commonMain
expect class PlatformFeature {
    fun doSomething(): Boolean
}

// androidMain — IMPLEMENT FIRST, full working impl
actual class PlatformFeature(private val context: Context) {
    actual fun doSomething(): Boolean = true
}

// iosMain — can stub initially
actual class PlatformFeature() {
    actual fun doSomething(): Boolean = false // TODO
}

// desktopMain — lowest priority
actual class PlatformFeature() {
    actual fun doSomething(): Boolean = false // TODO
}
```

---

## Migration Checklist (Android → KMP)

1. **Update `build.gradle.kts`** — switch to `kotlin.multiplatform` + add all targets
2. **Restructure source sets** — move `src/main/java/` → `src/commonMain/kotlin/`
3. **Replace Android deps:**
   - `R.string/drawable` → Compose Resources (`Res.*`)
   - `LocalContext` → `LocalPlatformContext`
   - Hilt → Koin
   - Android ViewModel → `org.jetbrains.androidx.lifecycle.ViewModel`
   - `room-ktx` → `room-runtime` in commonMain, ktx in androidMain only
   - Retrofit → Ktor
   - Gson/Moshi → `kotlinx.serialization`
4. **Move resources** to `commonMain/composeResources/`
5. **Platform-specific APIs** → `expect`/`actual` + separate source sets
6. **Tests** → move shared tests to `commonTest`, keep Android-specific in `androidUnitTest`

**Keep Android-only** (not worth migrating): `auth` (Firebase Auth SDK), `games`, ads in `androidApp`.
