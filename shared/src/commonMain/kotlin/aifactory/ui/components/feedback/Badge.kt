package aifactory.ui.components.feedback

import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * A standardized badge for displaying counts.
 * It handles the logic for coercing the count to a maximum of 99.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiBadge(modifier: Modifier = Modifier, count: Int) {
    if (count > 0) {
        Badge(modifier = modifier) {
            Text(count.coerceAtMost(99).toString())
        }
    }
}

/**
 * A standardized badge for displaying a simple notification dot.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiDotBadge(modifier: Modifier = Modifier) {
    Badge(modifier = modifier)
}
