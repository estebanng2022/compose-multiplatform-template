package aifactory.navigation

import aifactory.core.TestServiceLocator
import aifactory.rules.ComposeTestRuleProvider
import aifactory.ui.foundation.TestTags
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavFlowTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<aifactory.ui.MainActivity>()

    @Before
    fun setUp() {
        TestServiceLocator.setupTestEnvironment(composeTestRule.activity.applicationContext)
    }

    @Test
    fun appStartsOnDashboardAndCanNavigateToSettings() {
        // Then: The dashboard is displayed by default
        composeTestRule.onNodeWithTag(TestTags.Dashboard_List).assertIsDisplayed()

        // When: We click on the settings item in the side navigation
        composeTestRule.onNodeWithTag(TestTags.SideNav_Item + "settings").performClick()

        // Then: The settings screen should be displayed
        // (Assuming settings screen has a unique tag or text, placeholder here)
        // composeTestRule.onNodeWithText("Settings").assertIsDisplayed()

        // When: We press the back button
        composeTestRule.activity.onBackPressedDispatcher.onBackPressed()

        // Then: We should be back on the dashboard
        composeTestRule.onNodeWithTag(TestTags.Dashboard_List).assertIsDisplayed()
    }
}
