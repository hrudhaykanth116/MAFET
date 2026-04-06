# Code Conventions

## Package Structure

```
com.hrudhaykanth116.{module}.{layer}

com.hrudhaykanth116.todo.domain.model
com.hrudhaykanth116.todo.domain.use_cases
com.hrudhaykanth116.todo.data.repository
com.hrudhaykanth116.todo.ui.screens.list
com.hrudhaykanth116.todo.ui.models
```

---

## Naming

| Type | Pattern | Example |
|------|---------|---------|
| ViewModel | `{Screen}ViewModel` | `TodoListViewModel` |
| Use Case | `{Action}{Entity}UseCase` | `CreateTodoTaskUseCase` |
| Repository interface | `{Entity}Repository` | `ITodoRepository` |
| Repository impl | `{Entity}RepositoryImpl` | `TodoRepository` |
| UI State | `{Screen}UIState` / `{Screen}ScreenState` | `TodoListUIState` |
| Event | `{Screen}Event` | `TodoListScreenEvent` |
| Effect | `{Screen}Effect` | `TodoListEffect` |

---

## Logging

Use the project's own Logger facade — **not** Kermit directly:

```kotlin
import com.hrudhaykanth116.core.common.utils.log.Logger

Logger.d(TAG, "message")
Logger.e(TAG, "error message", exception)  // optional throwable

```

---

## UIText

Use `UIText` from `core-ui` for all user-visible strings:

```kotlin
// String resource (with optional format args)
UIText.StringRes(Res.string.todo_error_generic)

// Raw string
UIText.Text("some message")

// Extensions
"hello".toUIText()
Res.string.my_string.toUIText()

// In Composable — call getText()
Text(text = uiText.getText())
```

---

## DomainResult

```kotlin
// Chainable
result
    .onSuccess { data -> /* handle */ }
    .onError { error -> /* handle */ }

// Pattern-matched
when (result) {
    is DomainResult.Success -> setState { UIState.Idle(contentStateOrDefault.copy(...)) }
    is DomainResult.Error -> setState {
        UIState.Idle(contentStateOrDefault, userMessage = UIText.StringRes(Res.string.error).toErrorMessage())
    }
}
```

---

## Kotlin

**Immutable collections:**
```kotlin
import kotlinx.collections.immutable.ImmutableList

data class TodoListUIState(
    val tasks: ImmutableList<TodoTask> = persistentListOf()
)
```

**Inject `CoroutineDispatcher` for testability:**
```kotlin
class MyViewModel(
    private val dispatcher: CoroutineDispatcher
) : UIStateViewModel<...>(...) { ... }
```

**Serialization:**
```kotlin
@Serializable
data class TodoModel(
    val id: String,
    @SerialName("created_at") val createdAt: Long
)
```

---

## Navigation Routes

Routes are sealed classes with a string route property:

```kotlin
// Feature-level
sealed class TodoNavScreen(val route: String) {
    object TodoListScreen : TodoNavScreen("todo_list_screen")
    object CreateOrUpdateTodoScreen : TodoNavScreen("create_todo_screen/{id}")
}

// Top-level
sealed class HomeRoute(val route: String) {
    object Todo : HomeRoute("todo")
    fun withArgs(vararg args: String): String = buildString {
        append(route); args.forEach { append("/$it") }
    }
}
```

---

## Compose Screen Structure

```kotlin
@Composable
fun MyScreen(
    viewModel: MyViewModel = koinViewModel(),
    onBackClicked: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.initializeData()
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                MyEffect.NavigateBack -> onBackClicked()
            }
        }
    }

    val uiState by viewModel.uiStateFlow.collectAsState()

    MyScreenUI(uiState = uiState, onEvent = viewModel::processEvent)
}

@Composable
private fun MyScreenUI(
    uiState: UIState<MyState>,
    onEvent: (MyEvent) -> Unit
) {
    when (uiState) {
        is UIState.Loading -> CircularProgressIndicator()
        is UIState.Idle, is UIState.Error -> {
            val state = uiState.contentState ?: MyState()
            // render content
        }
    }
}
```

Key points:
- Call `viewModel.initializeData()` in `LaunchedEffect(Unit)`
- Use `viewModel.processEvent(...)` for user events (not `onEvent`)
- Use `koinViewModel { parametersOf(arg) }` when passing args to ViewModel
- Single-activity architecture: `MainActivity` in `androidApp`, Navigation Compose, no Fragments
