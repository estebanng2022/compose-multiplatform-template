package aifactory.rules

import aifactory.ui.MainActivity
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

/**
 * A JUnit rule that provides a ComposeContentTestRule for testing Composables in isolation or with an Activity.
 */
class ComposeTestRuleProvider : TestRule {
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    override fun apply(base: Statement, description: Description): Statement {
        return composeTestRule.apply(base, description)
    }

    fun runTest(body: ComposeContentTestRule.() -> Unit) {
        composeTestRule.runTest(body)
    }
}
