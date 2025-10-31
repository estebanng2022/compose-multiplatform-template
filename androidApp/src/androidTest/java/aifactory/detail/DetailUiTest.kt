package aifactory.detail

import aifactory.core.TestServiceLocator
import aifactory.ui.MainActivity
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
class DetailUiTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        TestServiceLocator.setupTestEnvironment(composeTestRule.activity.applicationContext)
    }

    @Test
    fun openDetail_andNavigateBack() {
        // Click to open the first item. A more specific click target is needed.
        composeTestRule.onNodeWithTag(TestTags.Dashboard_Item + "test_id_0").performClick()

        // Assert that the detail screen is shown
        composeTestRule.onNodeWithTag(TestTags.Detail_Header).assertIsDisplayed()

        // Navigate back
        composeTestRule.activity.onBackPressedDispatcher.onBackPressed()

        // Assert that we are back on the dashboard
        composeTestRule.onNodeWithTag(TestTags.Dashboard_List).assertIsDisplayed()
    }

    @Test
    fun shareButton_isDummy() {
        // Open detail view
        composeTestRule.onNodeWithTag(TestTags.Dashboard_Item + "test_id_0").performClick()

        // Click share
        composeTestRule.onNodeWithTag(TestTags.Detail_Share).performClick()

        // Nothing should happen, this is a placeholder test. No crash is a pass.
    }
}
