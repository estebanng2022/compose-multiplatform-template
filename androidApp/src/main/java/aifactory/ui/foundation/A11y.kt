package aifactory.ui.foundation

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalAccessibilityManager
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

fun Modifier.minTouchTarget(): Modifier = this.sizeIn(minWidth = 48.dp, minHeight = 48.dp)

fun Modifier.decorative(): Modifier = this.clearAndSetSemantics { }

fun Modifier.asButton(enabled: Boolean = true, selected: Boolean = false): Modifier = this.semantics {
    role = Role.Button
    this.selected = selected
}.focusable(enabled)

@Composable
fun Announce(message: String) {
    val accessibilityManager = LocalAccessibilityManager.current
    LaunchedEffect(message) {
        accessibilityManager?.announceForAccessibility(message)
    }
}
