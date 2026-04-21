package com.hrudhaykanth116.mafet

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import com.hrudhaykanth116.composeapp.testing.TestTags
import com.hrudhaykanth116.mafet.main.MainActivity
import org.junit.Rule
import org.junit.Test

/**
 * Checks that the app starts up and reaches the Dashboard without crashing.
 */
class AppSmokeTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun appLaunches_showsDashboard() {
        // Wait up to 10s for the dashboard to appear — splash screen + remote config fetch can be slow.
        composeRule.waitUntil(timeoutMillis = 10_000) {
            composeRule.onAllNodesWithTag(TestTags.DASHBOARD_ROOT)
                .fetchSemanticsNodes().isNotEmpty()
        }
        composeRule.onNodeWithTag(TestTags.DASHBOARD_ROOT).assertIsDisplayed()
    }
}
