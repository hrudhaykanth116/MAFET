package com.hrudhaykanth116.mafet

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.hrudhaykanth116.composeapp.testing.TestTags
import com.hrudhaykanth116.mafet.main.MainActivity
import org.junit.Rule
import org.junit.Test

/**
 * End-to-end flow: type a title in the quick-add bar and confirm it shows up in the list.
 */
class TodoFlowTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun addTodo_appearsInList() {
        // Timestamp suffix keeps the title unique across repeated runs so stale DB rows don't cause false passes.
        val newTodoTitle = "Buy milk ${System.currentTimeMillis()}"
        val todoNavTag = TestTags.bottomNavItem("TODO")

        composeRule.waitUntil(timeoutMillis = 10_000) {
            composeRule.onAllNodesWithTag(todoNavTag).fetchSemanticsNodes().isNotEmpty()
        }
        composeRule.onNodeWithTag(todoNavTag).performClick()

        composeRule.waitUntil(timeoutMillis = 5_000) {
            composeRule.onAllNodesWithTag(TestTags.TODO_TITLE_INPUT)
                .fetchSemanticsNodes().isNotEmpty()
        }

        composeRule.onNodeWithTag(TestTags.TODO_TITLE_INPUT).performTextInput(newTodoTitle)
        composeRule.onNodeWithTag(TestTags.TODO_ADD_BUTTON).performClick()

        // Room write + Flow emission + recompose takes a moment, so wait before asserting.
        composeRule.waitUntil(timeoutMillis = 5_000) {
            composeRule.onAllNodesWithText(newTodoTitle).fetchSemanticsNodes().isNotEmpty()
        }
        composeRule.onNodeWithText(newTodoTitle).assertIsDisplayed()
    }
}
