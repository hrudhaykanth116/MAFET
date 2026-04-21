package com.hrudhaykanth116.mafet

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft
import com.hrudhaykanth116.composeapp.testing.TestTags
import com.hrudhaykanth116.mafet.main.MainActivity
import org.junit.Rule
import org.junit.Test

/**
 * Additional todo list tests covering edge cases not in TodoFlowTest.
 */
class TodoListTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    /**
     * Shared setup: tap the TODO tab and wait for the list screen to be ready.
     */
    private fun navigateToTodo() {
        val todoNavTag = TestTags.bottomNavItem("TODO")
        composeRule.waitUntil(timeoutMillis = 10_000) {
            composeRule.onAllNodesWithTag(todoNavTag).fetchSemanticsNodes().isNotEmpty()
        }
        composeRule.onNodeWithTag(todoNavTag).performClick()
        composeRule.waitUntil(timeoutMillis = 5_000) {
            composeRule.onAllNodesWithTag(TestTags.TODO_LIST_ROOT).fetchSemanticsNodes().isNotEmpty()
        }
    }

    /**
     * Tapping the + button with nothing typed should open the full Create screen,
     * not silently create a blank todo.
     */
    @Test
    fun addTodo_withEmptyTitle_navigatesToCreateScreen() {
        navigateToTodo()
        composeRule.waitUntil(timeoutMillis = 5_000) {
            composeRule.onAllNodesWithTag(TestTags.TODO_ADD_BUTTON).fetchSemanticsNodes().isNotEmpty()
        }

        composeRule.onNodeWithTag(TestTags.TODO_ADD_BUTTON).performClick()

        composeRule.waitUntil(timeoutMillis = 5_000) {
            composeRule.onAllNodesWithText("Create Todo task").fetchSemanticsNodes().isNotEmpty()
        }
        composeRule.onNodeWithText("Create Todo task").assertIsDisplayed()
    }

    /**
     * Add two items back-to-back and confirm both survive in the list (not just the last one).
     */
    @Test
    fun addMultipleTodos_bothAppearInList() {
        navigateToTodo()
        val ts = System.currentTimeMillis()
        val title1 = "First task $ts"
        val title2 = "Second task $ts"

        composeRule.waitUntil(timeoutMillis = 5_000) {
            composeRule.onAllNodesWithTag(TestTags.TODO_TITLE_INPUT).fetchSemanticsNodes().isNotEmpty()
        }

        composeRule.onNodeWithTag(TestTags.TODO_TITLE_INPUT).performTextInput(title1)
        composeRule.onNodeWithTag(TestTags.TODO_ADD_BUTTON).performClick()
        composeRule.waitUntil(timeoutMillis = 5_000) {
            composeRule.onAllNodesWithText(title1).fetchSemanticsNodes().isNotEmpty()
        }

        composeRule.onNodeWithTag(TestTags.TODO_TITLE_INPUT).performTextInput(title2)
        composeRule.onNodeWithTag(TestTags.TODO_ADD_BUTTON).performClick()
        composeRule.waitUntil(timeoutMillis = 5_000) {
            composeRule.onAllNodesWithText(title2).fetchSemanticsNodes().isNotEmpty()
        }

        composeRule.onNodeWithText(title1).assertIsDisplayed()
        composeRule.onNodeWithText(title2).assertIsDisplayed()
    }

    /**
     * Swipe left on a todo item (EndToStart dismiss) and confirm it's deleted from the list.
     */
    @Test
    fun swipeTodo_removesFromList() {
        navigateToTodo()
        val title = "Swipe to delete ${System.currentTimeMillis()}"

        composeRule.waitUntil(timeoutMillis = 5_000) {
            composeRule.onAllNodesWithTag(TestTags.TODO_TITLE_INPUT).fetchSemanticsNodes().isNotEmpty()
        }
        composeRule.onNodeWithTag(TestTags.TODO_TITLE_INPUT).performTextInput(title)
        composeRule.onNodeWithTag(TestTags.TODO_ADD_BUTTON).performClick()
        // Wait for the item's own test tag to appear before trying to swipe it.
        composeRule.waitUntil(timeoutMillis = 5_000) {
            composeRule.onAllNodesWithTag(TestTags.todoItem(title)).fetchSemanticsNodes().isNotEmpty()
        }

        composeRule.onNodeWithTag(TestTags.todoItem(title)).performTouchInput { swipeLeft() }

        // Room delete + Flow emission takes a moment to propagate back to the UI.
        composeRule.waitUntil(timeoutMillis = 5_000) {
            composeRule.onAllNodesWithTag(TestTags.todoItem(title)).fetchSemanticsNodes().isEmpty()
        }
        assert(
            composeRule.onAllNodesWithTag(TestTags.todoItem(title)).fetchSemanticsNodes().isEmpty()
        ) { "Todo '$title' should be removed after swipe" }
    }
}
