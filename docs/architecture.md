# Architecture

## Clean Architecture Layers

### Presentation
**Modules:** `core-ui`, `features/*`

- Composable screens and reusable UI components
- ViewModels extending `UIStateViewModel` (UIState / Event / Effect)
- UI models and domain→UI mappers

### Domain
**Module:** `core-domain`

- Domain models (pure Kotlin data classes)
- Repository interfaces (`ITodoRepository`, etc.) — I-prefixed
- Use cases — one public `invoke()` function each
- `DomainResult<T>` and `DomainError` — shared result types

### Data
**Modules:** `core-data`, `core-network`

- Repository implementations that fulfill domain interfaces
- Room entities, DAOs, and database setup
- Ktor API service definitions and response models
- Entity ↔ domain mappers

### Foundation
**Module:** `core-common`

- Pure Kotlin — no platform or framework dependencies
- Shared utilities: extensions, `Logger` facade, date/time helpers
- Does not depend on any other module in the project

---

## Module Dependency Order


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
                    ┌────────▼────────┐
                    │   core-domain   │
                    └────────┬────────┘
                             │
                    ┌────────▼────────┐
                    │    core-data    │
                    └────────┬────────┘
                             │
                    ┌────────▼────────┐
                    │  core-network   │
                    └────────┬────────┘
                             │
                    ┌────────▼────────┐
                    │   core-common   │
                    └─────────────────┘
```


- `core-common` is the foundation — pure Kotlin, no dependencies on any other module.
- All core modules ultimately depend on `core-common`.
- `composeApp` wires together all feature modules (todo, weather, tv) and the core stack.
- `androidApp`, `iosApp`, and `desktopApp` are platform entry points that depend on `composeApp`.

---

## UIState

```kotlin
sealed class UIState<T>(open val contentState: T?) {
    data class Loading<T>(override val contentState: T? = null, val message: UIText? = null) : UIState<T>(contentState)
    data class Error<T>(val errorState: DomainError, override val contentState: T? = null) : UIState<T>(contentState)
    data class Idle<T>(override val contentState: T? = null, val userMessage: UserMessage? = null) : UIState<T>(contentState)
}
```

**No `Success` state** — Idle serves as the resting/success state.

---

## UIStateViewModel

Base class for all ViewModels. Subclasses must implement `initializeData()` and `processEvent()`.

```kotlin
class MyViewModel(
    private val useCase: MyUseCase,
    networkMonitor: NetworkMonitor,
    private val dispatcher: CoroutineDispatcher
) : UIStateViewModel<MyState, MyEvent, MyEffect>(
    initialState = UIState.Loading(MyState()),
    defaultState = MyState(),
    networkMonitor = networkMonitor
) {
    override fun initializeData() {
        viewModelScope.launch(dispatcher) { /* load data */ }
    }

    override fun processEvent(event: MyEvent) {
        when (event) {
            is MyEvent.SomeAction -> modifyState(event.data)
        }
    }
}
```

**Effects are not used. Instead, States are only used.**

**Available protected methods:**
- `setIdleState { copy(...) }` — update content state, stay Idle
- `setLoadingState(contentState, message)` — transition to Loading
- `setEffect(effect)` — emit a one-time side effect
- `setState { ... }` — full UIState replacement
- `contentStateOrDefault` — current content state or default
- `showUserMessage(message)` — show snackbar/toast via Idle state
- `isNetworkAvailable()` — check connectivity

---

## Koin DI Pattern

```kotlin
val myModule = module {
    // Singletons (shared instance)
    single<IMyRepository> {
        MyRepositoryImpl(get(), dispatcher = get(named(DispatchersEnum.IoDispatcher)))
    }

    // New instance per injection
    factory { MyUseCase(get()) }

    // ViewModels registered as factory (not viewModel {})
    factory {
        MyViewModel(
            useCase = get(),
            networkMonitor = get(),
            dispatcher = get(named(DispatchersEnum.MainDispatcher))
        )
    }

    // ViewModel with parameters
    factory { (id: String?) ->
        MyDetailViewModel(useCase = get(), id = id)
    }
}
```

Note: ViewModels are registered with `factory { }`, not `viewModel { }`.

---

## Feature Module Structure

```
features/{module}/src/
  commonMain/kotlin/com/hrudhaykanth116/{module}/
    data/
      data_source/local/   # Room DAOs
      local/room/          # Database, entities
      mappers/             # Entity ↔ Domain
      repositories/        # Repository impls
    domain/
      model/               # Domain models
      repository/          # Repository interfaces (I-prefixed)
      use_cases/           # Business logic
    ui/
      components/          # Reusable composables
      mappers/             # Domain → UI model
      models/              # UIState, Event, Effect
      screens/             # Screen composables + ViewModels
    di/                    # Koin module
    navigation/            # Route sealed classes
  androidMain/kotlin/      # Android-specific (e.g., DB builder)
  iosMain/kotlin/
  desktopMain/kotlin/
  commonTest/kotlin/
```

---

## core-ui Components

**Shared components:** `AppCard`, `AppInputText`, `AppScreen`, `AppToolbar`, `AppProgressBar`, `AppSearchBar`, `AppImage`, `AppDialog`, `AppDropDown`, `AppText`, `AppIcon`, etc.