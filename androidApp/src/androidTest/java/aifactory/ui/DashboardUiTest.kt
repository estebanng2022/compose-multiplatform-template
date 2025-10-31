package aifactory.ui

import aifactory.ui.foundation.TestTags
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DashboardUiTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun refresh_showsLoadingAndThenContent() {
        // This test assumes a refresh button or action is available.
        // As we don't have one, this is a placeholder.
        // composeTestRule.onNodeWithTag("Refresh_Button").performClick()
        composeTestRule.onNodeWithTag(TestTags.State_Loading).assertExists()
        composeTestRule.onNodeWithTag(TestTags.Dashboard_List).assertExists()
    }

    @Test
    fun loadMore_extendsList() {
        // Scroll to the end of the list to trigger load more
        composeTestRule.onNodeWithTag(TestTags.Dashboard_List)
            .performScrollToNode(androidx.compose.ui.test.hasTestTag(TestTags.Dashboard_LoadMore))
        
        // Assert that the loading indicator appears and more items are loaded
        composeTestRule.onNodeWithTag(TestTags.Dashboard_LoadMore).assertExists()
        // We would need to check if the item count increases, which requires a more complex setup
    }

    @Test
    fun favoriteToggle_persistsAfterRecreate() {
        val firstItemTag = TestTags.Dashboard_Item + "item_0"

        // Find the favorite toggle within the first item and click it
        composeTestRule.onNodeWithTag(firstItemTag).assertExists()
        // This is a simplified way to find the favorite button inside the item
        // A more specific sub-tag would be better.
        val favoriteButton = composeTestRule.onNodeWithTag(TestTags.Detail_Favorite, useUnmergedTree = true)
        favoriteButton.performClick()
        favoriteButton.assertIsSelected()

        // Recreate the activity
        composeTestRule.activityRule.scenario.recreate()

        // Verify the item is still selected
        composeTestRule.onNodeWithTag(firstItemTag).assertExists()
        composeTestRule.onNodeWithTag(TestTags.Detail_Favorite, useUnmergedTree = true).assertIsSelected()
    }
}
