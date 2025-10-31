package aifactory.ui

import aifactory.ui.foundation.TestTags
import aifactory.util.ComposeRule
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class NavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun appStartsOnDashboard() {
        composeTestRule.onNodeWithTag(TestTags.Dashboard_List).assertIsDisplayed()
    }

    @Test
    fun sideNavNavigationAndBackStack() {
        // Navigate to Settings
        composeTestRule.onNodeWithTag(TestTags.SideNav_Item + "settings").performClick()
        // TODO: Assert that settings content is displayed

        // Navigate to About
        composeTestRule.onNodeWithTag(TestTags.SideNav_Item + "about").performClick()
        // TODO: Assert that about content is displayed

        // Press back
        composeTestRule.activity.onBackPressedDispatcher.onBackPressed()

        // Assert that we are back on the Settings screen
        // TODO: Assert that settings content is displayed
    }
}
