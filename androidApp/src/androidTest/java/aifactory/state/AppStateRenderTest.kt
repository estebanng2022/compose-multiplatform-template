package aifactory.state

import aifactory.ui.foundation.TestTags
import aifactory.ui.foundation.UiStrings
import aifactory.ui.widgets.states.AppState
import aifactory.ui.widgets.states.RenderAppState
import androidx.compose.material3.Text
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test

class AppStateRenderTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loadingState_showsLoadingIndicator() {
        composeTestRule.setContent {
            RenderAppState(state = AppState.Loading) {
                // Content should not be rendered
            }
        }

        composeTestRule.onNodeWithTag(TestTags.State_Loading).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(UiStrings.loading).assertIsDisplayed()
    }

    @Test
    fun errorState_showsErrorMessageAndRetryButton() {
        val errorMessage = "Something went wrong"
        composeTestRule.setContent {
            RenderAppState(state = AppState.Error(errorMessage)) {
                // Content should not be rendered
            }
        }

        composeTestRule.onNodeWithTag(TestTags.State_Error).assertIsDisplayed()
        composeTestRule.onNodeWithText(errorMessage, substring = true).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TestTags.Button_Retry).assertIsDisplayed()
    }

    @Test
    fun emptyState_showsEmptyMessage() {
        val emptyMessage = "No data available"
        composeTestRule.setContent {
            RenderAppState(state = AppState.Empty(emptyMessage)) {
                // Content should not be rendered
            }
        }

        composeTestRule.onNodeWithTag(TestTags.State_Empty).assertIsDisplayed()
        composeTestRule.onNodeWithText(emptyMessage).assertIsDisplayed()
    }

    @Test
    fun contentState_showsContent() {
        val contentText = "This is the content"
        composeTestRule.setContent {
            RenderAppState(state = AppState.Content("data")) {
                Text(contentText)
            }
        }

        composeTestRule.onNodeWithText(contentText).assertIsDisplayed()
    }
}
