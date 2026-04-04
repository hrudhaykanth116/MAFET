# MAFET - Multi-platform Utilities App

<p align="center">
  <img src="https://img.shields.io/badge/Kotlin-2.2.0-7F52FF?logo=kotlin&logoColor=white" alt="Kotlin">
  <img src="https://img.shields.io/badge/Compose%20Multiplatform-1.10.1-4285F4?logo=jetpackcompose&logoColor=white" alt="Compose Multiplatform">
  <img src="https://img.shields.io/badge/Platforms-Android%20%7C%20iOS%20%7C%20Desktop-green" alt="Platforms">
  <img src="https://img.shields.io/badge/Architecture-Clean%20%7C%20MVI-orange" alt="Architecture">
  <img src="https://img.shields.io/badge/License-MIT-blue" alt="License">
</p>

<p align="center">
  <b>A Kotlin Multiplatform application providing essential daily utilities across Android, iOS, and Desktop platforms.</b>
</p>

---

## Overview

MAFET is a modern, feature-rich utility application built with **Kotlin Multiplatform (KMP)** and **Compose Multiplatform**. Originally developed as an Android app, it has been evolved into a cross-platform solution demonstrating production-grade architecture patterns and the latest in Kotlin development.

### Why MAFET?

- **True Cross-Platform**: Share up to 90% of code across Android, iOS, and Desktop
- **Modern Architecture**: Clean Architecture with MVVM + MVI for scalable, testable code
- **Production-Ready Patterns**: Real-world implementation of dependency injection, state management, and offline-first design
- **Comprehensive Testing**: Unit tests across all platforms with MockK

---

## Features

### Todo List
**Status**: Production Ready | **Platforms**: Android, iOS, Desktop

<details>
<summary>Click to expand details</summary>

A full-featured task management system with:
- Create, update, and delete tasks with rich metadata
- **Category filtering** (Work, Personal, Shopping, Health, etc.)
- **Priority levels** with visual indicators
- **Due date/time** tracking with reminders
- **Search and sort** functionality
- **Sync status** tracking for offline support
- **Shared element transitions** for smooth navigation
- Room database with KMP support

**Tech Stack**: Room KMP, Kotlin Coroutines, Flow, Compose Multiplatform

</details>

---

### TV Shows Tracking
**Status**: Production Ready | **Platforms**: Android, iOS

<details>
<summary>Click to expand details</summary>

Track your favorite TV shows powered by **TMDB API**:
- **Home screen** with categorized show lists (Trending, Popular, Top Rated, Airing Today)
- **Search** functionality with instant results
- **Detailed show information** (cast, seasons, episodes, ratings)
- **Personal watchlist** with watch status tracking
- **Similar shows** recommendations
- Beautiful poster and backdrop image displays
- Offline caching with Room database

**Tech Stack**: Ktor Client, TMDB API, Room KMP, Coil 3, Paging 3

</details>

---

### Weather Forecast
**Status**: Production Ready | **Platforms**: Android, iOS

<details>
<summary>Click to expand details</summary>

Real-time weather information with:
- **Current conditions** with temperature, humidity, wind speed
- **Hourly forecast** with weather icons
- **Daily forecast** for upcoming days
- **Location-based** weather using GPS
- **Search by city** with geocoding
- Beautiful weather-themed UI
- Pull-to-refresh for latest data
- **DataStore** for caching last known location

**Tech Stack**: OpenWeather API, Ktor Client, Location Services (platform-specific), DataStore

</details>

---

### Journal
**Status**: Development | **Platforms**: Android, iOS, Desktop

<details>
<summary>Click to expand details</summary>

Personal note-taking with emotional awareness:
- Create and manage journal entries
- **Emotion slider** for mood tracking
- Rich text content
- Date-based organization
- Sync status tracking
- Full offline support with Room

**Tech Stack**: Room KMP, Compose Multiplatform, Flow

</details>

---

### AI Assistant
**Status**: Development | **Platforms**: Android, iOS, Desktop

<details>
<summary>Click to expand details</summary>

AI-powered query system:
- Natural language query interface
- Response streaming
- Firebase Vertex AI integration (Android)
- Platform-specific implementations via expect/actual

**Tech Stack**: Firebase Vertex AI, expect/actual pattern

</details>

---

### Media Gallery
**Status**: Development | **Platforms**: Android, iOS, Desktop

<details>
<summary>Click to expand details</summary>

Explore stunning photos and videos from **Pexels**:
- **Curated content** feed
- **Search** with filters (orientation, color, size)
- **Staggered grid** layout
- **Detail view** with color palette extraction
- Download and share functionality

**Tech Stack**: Pexels API, Ktor Client, Coil 3

</details>

---

### Authentication
**Status**: Complete | **Platforms**: Android Only

<details>
<summary>Click to expand details</summary>

Firebase-powered authentication:
- Email/password login and signup
- Form validation with real-time feedback
- User profile management
- Session persistence

**Tech Stack**: Firebase Auth, Firebase Realtime Database

</details>

---

### Games
**Status**: Experimental | **Platforms**: Android Only

<details>
<summary>Click to expand details</summary>

Sprite-based game experiments:
- Custom sprite animation system
- Touch gesture detection
- Game state management

**Tech Stack**: Compose Canvas, Custom Sprite Engine

</details>

---

## Platform Support Matrix

| Feature | Android | iOS | Desktop | Status |
|---------|:-------:|:---:|:-------:|--------|
| Todo List | Full | Full | Full | Production |
| TV Shows | Full | Full | Planned | Production |
| Weather | Full | Full | Planned | Production |
| Journal | Full | Full | Full | Development |
| AI Assistant | Full | Partial | Partial | Development |
| Media Gallery | Full | Full | Full | Development |
| Authentication | Full | - | - | Complete |
| Games | Full | - | - | Experimental |

---

## Architecture

MAFET follows **Clean Architecture** principles with a clear separation of concerns across three layers:

```
┌─────────────────────────────────────────────────────────────────┐
│                      Presentation Layer                          │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────────────────┐ │
│  │   Screens   │  │  ViewModels │  │   UI State / Events     │ │
│  │  (Compose)  │◄─┤ (MVI/MVVM)  │◄─┤   Effects               │ │
│  └─────────────┘  └─────────────┘  └─────────────────────────┘ │
└────────────────────────────┬────────────────────────────────────┘
                             │
┌────────────────────────────▼────────────────────────────────────┐
│                       Domain Layer                               │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────────────────┐ │
│  │  Use Cases  │  │   Models    │  │  Repository Interfaces  │ │
│  └─────────────┘  └─────────────┘  └─────────────────────────┘ │
└────────────────────────────┬────────────────────────────────────┘
                             │
┌────────────────────────────▼────────────────────────────────────┐
│                        Data Layer                                │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────────────────┐ │
│  │Repositories │  │Data Sources │  │   Network / Database    │ │
│  │   (Impl)    │  │ (Local/API) │  │   (Ktor / Room)         │ │
│  └─────────────┘  └─────────────┘  └─────────────────────────┘ │
└─────────────────────────────────────────────────────────────────┘
```

### UI State Management (MVI Pattern)

```
┌─────────┐    Events    ┌─────────────┐    State    ┌─────────┐
│   UI    │─────────────►│  ViewModel  │────────────►│   UI    │
│(Screen) │              │(UIStateVM)  │             │(Screen) │
└─────────┘              └─────────────┘             └─────────┘
     ▲                         │
     │         Effects         │
     └─────────────────────────┘
```

**UIStateViewModel** provides:
- `UIState<T>` - Loading, Idle, Error states with content
- `Event` - User interactions (clicks, input changes)
- `Effect` - One-time side effects (navigation, toasts)

### Module Dependency Graph

```
                    ┌─────────────────┐
                    │    androidApp   │
                    │    desktopApp   │
                    │      iosApp     │
                    └────────┬────────┘
                             │
                    ┌────────▼────────┐
                    │    composeApp   │
                    │  (Shared Entry) │
                    └────────┬────────┘
                             │
        ┌────────────────────┼────────────────────┐
        │                    │                    │
   ┌────▼────┐         ┌─────▼─────┐        ┌────▼────┐
   │  todo   │         │    tv     │        │ weather │
   │ journal │         │   media   │        │   ai    │
   └────┬────┘         └─────┬─────┘        └────┬────┘
        │                    │                   │
        └────────────────────┼───────────────────┘
                             │
                    ┌────────▼────────┐
                    │     core-ui     │
                    └────────┬────────┘
                             │
        ┌────────────────────┼────────────────────┐
        │                    │                    │
   ┌────▼────┐         ┌─────▼─────┐        ┌────▼────┐
   │core-data│         │core-network│       │core-domain│
   └────┬────┘         └─────┬─────┘        └────┬────┘
        │                    │                   │
        └────────────────────┼───────────────────┘
                             │
                    ┌────────▼────────┐
                    │   core-common   │
                    └─────────────────┘
```

---

## Tech Stack

### Core Technologies

| Category | Technology | Version |
|----------|-----------|---------|
| Language | Kotlin | 2.2.0 |
| UI Framework | Compose Multiplatform | 1.10.1 |
| Build System | Gradle (Kotlin DSL) | 8.11 |
| DI Framework | Koin | 4.0.3 |

### Networking & Data

| Library | Purpose |
|---------|---------|
| Ktor Client 3.0.2 | HTTP networking (KMP) |
| Room 2.7.2 | Local database (KMP) |
| DataStore 1.1.1 | Preferences storage |
| Kotlinx Serialization | JSON parsing |

### Android Specific

| Library | Purpose |
|---------|---------|
| Firebase BOM 33.16 | Auth, Vertex AI, Database |
| Play Services Location | GPS location |
| Play Services Ads | Monetization |
| WorkManager | Background sync |

### UI & Media

| Library | Purpose |
|---------|---------|
| Coil 3.0.4 | Image loading (KMP) |
| Navigation Compose 2.9.2 | Type-safe navigation |
| Material 3 | Design system |
| Lottie | Animations |

### Testing

| Library | Purpose |
|---------|---------|
| JUnit 4.13.2 | Test framework |
| MockK 1.14.5 | Mocking library |
| Kotlinx Coroutines Test | Coroutine testing |

---

## Getting Started

### Prerequisites

- **JDK 17** or higher
- **Android Studio Ladybug** (2024.2+) or **IntelliJ IDEA**
- **Xcode 15+** (for iOS development)
- **Kotlin Multiplatform Mobile plugin**

### API Keys Setup

This project requires API keys from 3 external services. Both files below are gitignored — you must create them locally after cloning.

#### Android — `secrets.properties` (project root)

```properties
PEXELS_API_KEY="your_pexels_api_key"
TMDB_API_KEY="your_tmdb_api_key"
OPEN_WEATHER_FORECAST_API_KEY="your_openweather_api_key"
OPEN_WEATHER_GEO_CODING_API_KEY="your_openweather_api_key"
```

Keys are automatically injected into `BuildConfig` at compile time.

#### iOS — `iosApp/secrets.xcconfig`

Copy `iosApp/secrets.xcconfig.example` → `iosApp/secrets.xcconfig` and fill in:

```
TMDB_API_KEY = your_tmdb_api_key
OPEN_WEATHER_FORECAST_API_KEY = your_openweather_api_key
OPEN_WEATHER_GEO_CODING_API_KEY = your_openweather_api_key
PEXELS_API_KEY = your_pexels_api_key
```

Then wire it in Xcode: click the project root → **Info** tab → under **Configurations**, set `secrets.xcconfig` for both Debug and Release under the `iosApp` target.

#### Where to get the keys

| Service | Get your key | Used in |
|---------|-------------|---------|
| **TMDB** | https://www.themoviedb.org/settings/api | TV Shows module |
| **OpenWeatherMap** | https://home.openweathermap.org/api_keys | Weather module |
| **Pexels** | https://www.pexels.com/api/ | Media module |

### Build & Run

```bash
# Clone the repository
git clone https://github.com/hrudhaykanth116/MAFET.git
cd MAFET

# Build the project
./gradlew build

# Run Android app
./gradlew :androidApp:installDebug

# Run Desktop app
./gradlew :desktopApp:run

# Run tests
./gradlew allTests
```

### iOS Setup

1. Open `iosApp/iosApp.xcodeproj` in Xcode
2. Select your development team
3. Build and run on simulator or device

---

## Project Structure

```
MAFET/
├── androidApp/          # Android entry point with ads
├── desktopApp/          # Desktop JVM application
├── iosApp/              # iOS SwiftUI wrapper
├── composeApp/          # Shared Compose UI
│
├── core-common/         # Pure Kotlin utilities
├── core-domain/         # Domain result types
├── core-data/           # Data layer abstractions
├── core-network/        # Ktor client setup
├── core-ui/             # Shared UI components
│
├── features/
│   ├── todo/            # Task management (KMP)
│   ├── tv/              # TV show tracking (KMP)
│   ├── weather/         # Weather forecast (KMP)
│   ├── journal/         # Note-taking (KMP)
│   ├── ai/              # AI features (KMP)
│   ├── media/           # Media gallery (KMP)
│   ├── auth/            # Authentication (Android)
│   └── games/           # Games (Android)
│
├── gradle/              # Version catalogs
└── buildSrc/            # Build conventions
```

---

## Roadmap

### In Progress
- [ ] Desktop support for TV Shows module
- [ ] Desktop support for Weather module
- [ ] Offline-first sync for all modules
- [ ] Widget support for Android

### Planned
- [ ] iOS native integrations (HealthKit, Shortcuts)
- [ ] Watch OS companion app
- [ ] Web support (Compose for Web)
- [ ] End-to-end encryption for Journal

### Future Ideas
- [ ] AI-powered task suggestions
- [ ] Social features for TV tracking
- [ ] Weather alerts and notifications

---

## Testing

```bash
# Run all tests
./gradlew allTests

# Run platform-specific tests
./gradlew testDebugUnitTest      # Android
./gradlew desktopTest            # Desktop
./gradlew iosSimulatorArm64Test  # iOS

# Run module-specific tests
./gradlew :features:todo:allTests
./gradlew :features:tv:testDebugUnitTest
```

---

## APIs Used

- [OpenWeather API](https://openweathermap.org/api) - Weather data
- [The Movie Database (TMDB)](https://www.themoviedb.org/) - TV show information
- [Pexels API](https://www.pexels.com/api/) - Stock photos and videos
- [Firebase](https://firebase.google.com/) - Authentication and AI

---

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

---

## Author

**Hrudhay Kanth Thangella**

[![LinkedIn](https://img.shields.io/badge/LinkedIn-Connect-0A66C2?logo=linkedin)](https://www.linkedin.com/in/hrudhay-thangella)
[![Email](https://img.shields.io/badge/Email-Contact-EA4335?logo=gmail)](mailto:hrudhaykanth116@gmail.com)
[![GitHub](https://img.shields.io/badge/GitHub-Follow-181717?logo=github)](https://github.com/hrudhaykanth116)

---

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.

---

<p align="center">
  <b>Built with Kotlin Multiplatform</b>
</p>
