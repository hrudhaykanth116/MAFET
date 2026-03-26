# MAFET Project - Claude Instructions

This file contains project-specific instructions and context for Claude Code.

---

## Project Overview

MAFET is a Kotlin Multiplatform (KMP) project currently in active migration from a native Android application to a cross-platform application using Compose Multiplatform (CMP). 
The migration is progressing incrementally, with most common code already converted to KMP-compatible implementations.

### Migration Status

**Completed:**
- iOS target configured and operational in `composeApp` module
- Compose Multiplatform resources system integrated across KMP modules

**In Progress:**
- Core infrastructure modules (`core-common`, `core-ui`, `core-data`, `core-network`) are being made fully KMP compatible for iOS
- The `todo` feature module is migrated to KMP and being adapted for iOS support
- Additional feature modules are being migrated incrementally to support Android and iOS
- Platform-specific implementations being extracted to appropriate source sets (androidMain, iosMain)

**Android-Only (Pending Migration):**
- `app` module - primary Android application entry point
- `core` module - contains Android-specific utilities and legacy code (including Google Mobile Ads integration)
- Feature modules not yet migrated: `ai`, `weather`, `tv`, `auth`, `journal`, `media`, `games`
- `training` module

---

## Architecture

### Module Structure

The project follows a modular architecture with clear separation between core infrastructure, feature modules, and platform-specific applications.

#### Core Modules (KMP Compatible)

**`core-common`** - Foundation layer
Dependencies: None
Contains: Common utilities, serialization, coroutines, dependency injection (Koin), logging (Kermit), date/time handling
Target Platforms: Android, iOS

**`core-data`** - Data layer
Dependencies: `core-common`
Contains: Room database (KMP compatible), data repositories, data models
Target Platforms: Android, iOS
Note: Room runtime is in commonMain, Room KTX extensions are Android-only (androidMain)

**`core-network`** - Network layer
Dependencies: `core-common`
Contains: Ktor client, API definitions, network utilities
Target Platforms: Android, iOS

**`core-ui`** - UI foundation layer
Dependencies: `core-common`, `core-data`
Contains: Shared UI components, themes, resources (drawables, strings), Compose utilities, Coil 3 image loading, navigation utilities
Target Platforms: Android, iOS
Note: Uses Compose Multiplatform Resources with public resource class generation

#### Android-Only Core Module

**`core`** - Legacy Android core
Contains: Android-specific utilities, Google Mobile Ads integration, legacy code being phased out
Platform: Android only

#### Feature Modules

**`features:todo`** - KMP compatible
Dependencies: `core-common`, `core-ui`, `core-data`
Target Platforms: Android, iOS
Status: Migrated to KMP with Room database integration, being adapted for iOS support

**Other feature modules** - Android-only (pending migration)
- `features:ai` (dynamic feature module)
- `features:weather`
- `features:tv`
- `features:auth`
- `features:journal`
- `features:media`
- `features:games`

#### Application Modules

**`app`** - Primary Android application
Dependencies: All feature modules, core modules
Platform: Android
Entry point: Main Android app with dynamic feature support

**`composeApp`** - Multiplatform application module
Platforms: Android, iOS
Contains: Shared application code and platform-specific entry points
iOS: Configured as static framework (iosX64, iosArm64, iosSimulatorArm64) for iOS integration
Status: Operational for both Android and iOS

#### Supporting Modules

**`home`** - Home screen/dashboard module (Android-only, pending migration)
**`training`** - Training/tutorial module (Android-only, pending migration)
**`features`** - Feature modules container

### Dependency Flow

```
Application Layer (app [Android], composeApp [KMP])
            ↓
Feature Modules (todo [KMP], weather, ai, etc.)
            ↓
Core UI Layer (core-ui [KMP])
            ↓
Core Infrastructure (core-data, core-network [KMP])
            ↓
Foundation Layer (core-common [KMP])
```

### Platform Support Matrix

| Module | Android | iOS | Status |
|--------|---------|-----|--------|
| core-common | ✅ | 🚧 | In Progress |
| core-data | ✅ | 🚧 | In Progress |
| core-network | ✅ | 🚧 | In Progress |
| core-ui | ✅ | 🚧 | In Progress |
| features:todo | ✅ | 🚧 | In Progress |
| composeApp | ✅ | ✅ | Operational |
| app | ✅ | ❌ | Android Only |
| Other features | ✅ | ❌ | Pending Migration |

---

## Development Guidelines

### KMP Development Principles

1. **Write KMP-first**: New code should be written in commonMain unless it requires platform-specific APIs
2. **expect/actual pattern**: Use only for legitimate platform differences (NetworkMonitor, ToastManager, platform dimensions)
3. **Resource handling**: Use Compose Multiplatform Resources (`Res.drawable.*`, `Res.string.*`) instead of Android R classes
4. **Dependencies**: Prefer multiplatform libraries; keep platform-specific dependencies in appropriate source sets (androidMain, iosMain)
5. **iOS Considerations**: Be mindful of iOS-specific limitations (no reflection, different threading model, static framework requirements)

### .gitignore Management
- Always create appropriate `.gitignore` files as per KMP standards when creating new modules or directories
- Include platform-specific ignores (iOS, Android)
- Add KMP-specific patterns (Kotlin/Native, Compose Multiplatform, KSP generated files, iOS framework artifacts)

### Module Creation Guidelines
- New feature modules should be created as KMP modules with Android, IOS support.
- Use `kotlin.multiplatform` plugin for all new modules
- Configure Compose Multiplatform resources if the module contains UI resources
- Set up proper source sets (commonMain, androidMain, iosMain)

---

