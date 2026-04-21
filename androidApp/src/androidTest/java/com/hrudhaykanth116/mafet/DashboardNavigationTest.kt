package com.hrudhaykanth116.mafet

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.hrudhaykanth116.composeapp.testing.TestTags
import com.hrudhaykanth116.mafet.main.MainActivity
import org.junit.Rule
import org.junit.Test

/**
 * Verifies bottom nav taps actually change the screen.
 */
class DashboardNavigationTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun clickingTodoBottomNav_navigatesToTodoList() {
        val todoNavTag = TestTags.bottomNavItem("TODO")

        // Dashboard → wait for bottom nav to be ready before tapping
        composeRule.waitUntil(timeoutMillis = 10_000) {
            composeRule.onAllNodesWithTag(todoNavTag).fetchSemanticsNodes().isNotEmpty()
        }

        composeRule.onNodeWithTag(todoNavTag).performClick()

        composeRule.waitUntil(timeoutMillis = 5_000) {
            composeRule.onAllNodesWithTag(TestTags.TODO_LIST_ROOT)
                .fetchSemanticsNodes().isNotEmpty()
        }
        composeRule.onNodeWithTag(TestTags.TODO_LIST_ROOT).assertIsDisplayed()
    }
}
