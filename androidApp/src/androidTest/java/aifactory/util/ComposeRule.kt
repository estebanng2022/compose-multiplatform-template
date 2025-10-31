package aifactory.util

import aifactory.ui.MainActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Rule

/**
 * A common test rule for setting up Compose UI tests with the app's main activity.
 */
class ComposeRule {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()
}
