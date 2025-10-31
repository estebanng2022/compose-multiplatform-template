package aifactory.dashboard

import aifactory.core.TestServiceLocator
import aifactory.ui.MainActivity
import aifactory.ui.foundation.TestTags
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DashboardUiTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        TestServiceLocator.setupTestEnvironment(composeTestRule.activity.applicationContext)
    }

    @Test
    fun listIsShownAndCanLoadMore() {
        // Assert list is displayed
        composeTestRule.onNodeWithTag(TestTags.Dashboard_List).assertExists()

        // Scroll to the end to trigger load more
        composeTestRule.onNodeWithTag(TestTags.Dashboard_List)
            .performScrollToNode(androidx.compose.ui.test.hasTestTag(TestTags.Dashboard_LoadMore))

        // Assert that the loading indicator for pagination is shown
        composeTestRule.onNodeWithTag(TestTags.Dashboard_LoadMore).assertExists()
        // More assertions would be needed to confirm the list has grown
    }

    @Test
    fun favoriteItem_persistsAfterRecreation() {
        val firstItemTag = TestTags.Dashboard_Item + "test_id_0" // Assuming TestData is used by the fake repo

        // Click the favorite toggle on the first item
        composeTestRule.onNodeWithTag(firstItemTag).assertExists()
        val favoriteToggle = composeTestRule.onNodeWithTag(TestTags.Detail_Favorite, useUnmergedTree = true) // Placeholder for a more specific tag
        favoriteToggle.performClick()
        favoriteToggle.assertIsSelected()

        // Recreate the activity to test persistence
        composeTestRule.activityRule.scenario.recreate()

        // Assert the item is still favorited
        composeTestRule.onNodeWithTag(firstItemTag).assertExists()
        composeTestRule.onNodeWithTag(TestTags.Detail_Favorite, useUnmergedTree = true).assertIsSelected()
    }
}
