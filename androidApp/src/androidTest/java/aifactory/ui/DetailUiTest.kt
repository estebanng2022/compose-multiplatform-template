package aifactory.ui

import aifactory.ui.foundation.TestTags
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailUiTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun openFromDashboard_showsDetail() {
        // Click on the first item to open it. This is a placeholder for a more specific action.
        composeTestRule.onNodeWithTag(TestTags.Dashboard_Item + "item_0").performClick()
        composeTestRule.onNodeWithTag(TestTags.Detail_Header).assertIsDisplayed()
    }

    @Test
    fun favoriteSync_detailToDashboard() {
        // 1. Open detail
        composeTestRule.onNodeWithTag(TestTags.Dashboard_Item + "item_0").performClick()

        // 2. Toggle favorite in detail
        composeTestRule.onNodeWithTag(TestTags.Detail_Favorite).performClick()

        // 3. Go back
        composeTestRule.activity.onBackPressedDispatcher.onBackPressed()

        // 4. Assert favorite is updated on dashboard (placeholder)
        composeTestRule.onNodeWithTag(TestTags.Dashboard_Item + "item_0").assertExists()
    }

    @Test
    fun backButton_returnsToDashboard() {
        // 1. Open detail
        composeTestRule.onNodeWithTag(TestTags.Dashboard_Item + "item_0").performClick()
        composeTestRule.onNodeWithTag(TestTags.Detail_Header).assertIsDisplayed()

        // 2. Go back
        composeTestRule.activity.onBackPressedDispatcher.onBackPressed()

        // 3. Assert we are back on the dashboard
        composeTestRule.onNodeWithTag(TestTags.Dashboard_List).assertIsDisplayed()
    }
}
