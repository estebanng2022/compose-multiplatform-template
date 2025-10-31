package aifactory.ui

import aifactory.ui.foundation.TestTags
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StatesUiTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun onLaunch_showsLoadingState() {
        // This test works if the app shows a loading indicator by default on launch
        composeTestRule.onNodeWithTag(TestTags.State_Loading).assertIsDisplayed()
    }

    @Test
    fun whenRepoReturnsEmpty_showsEmptyState() {
        // This requires injecting a mock repository that returns an empty list
        // For now, it's a placeholder for that test.
        // The test would look something like:
        // 1. Swap the real repository with a mock
        // 2. Trigger a refresh
        // 3. Assert that the node with TestTags.State_Empty is displayed
    }

    @Test
    fun whenRepoThrowsError_showsErrorState() {
        // This requires injecting a mock repository that throws an error
        // For now, it's a placeholder for that test.
        // The test would look something like:
        // 1. Swap the real repository with a mock that throws
        // 2. Trigger a refresh
        // 3. Assert that the node with TestTags.State_Error is displayed
        // 4. Assert that the node with TestTags.Button_Retry is displayed
    }
}
