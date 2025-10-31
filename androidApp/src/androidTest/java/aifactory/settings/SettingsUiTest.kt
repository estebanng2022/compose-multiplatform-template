package aifactory.settings

import aifactory.core.TestServiceLocator
import aifactory.ui.MainActivity
import aifactory.ui.foundation.TestTags
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SettingsUiTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        TestServiceLocator.setupTestEnvironment(composeTestRule.activity.applicationContext)
        // Navigate to settings screen first
        composeTestRule.onNodeWithTag(TestTags.SideNav_Item + "settings").performClick()
    }

    @Test
    fun toggleSetting_persistsAfterRecreation() {
        // Assuming a test tag for a specific toggle, e.g., "settings_toggle_autoSync"
        val toggleTag = "settings_toggle_autoSync"

        // Click the toggle
        composeTestRule.onNodeWithTag(toggleTag).performClick()
        composeTestRule.onNodeWithTag(toggleTag).assertIsOn()

        // Recreate the activity
        composeTestRule.activityRule.scenario.recreate()

        // Navigate back to settings and verify the toggle is still on
        composeTestRule.onNodeWithTag(TestTags.SideNav_Item + "settings").performClick()
        composeTestRule.onNodeWithTag(toggleTag).assertIsOn()
    }
}
