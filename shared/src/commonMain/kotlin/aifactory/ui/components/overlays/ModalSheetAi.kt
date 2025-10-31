package aifactory.ui.components.overlays

import androidx.compose.runtime.Composable

/**
 * A modal bottom sheet that provides content on top of the existing screen.
 * This is an expect declaration, requiring platform-specific implementations.
 */
@Composable
expect fun ModalSheetAi(
    visible: Boolean,
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
)
