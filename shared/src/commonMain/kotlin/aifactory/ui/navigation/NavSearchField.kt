package aifactory.ui.navigation

import aifactory.ui.components.inputs.SearchFieldCompact
import aifactory.ui.foundation.Spacing
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * A search field specifically styled for use within a navigation component.
 */
@Composable
fun NavSearchField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "Search..."
) {
    SearchFieldCompact(
        value = value,
        onValueChange = onValueChange,
        placeholder = placeholder,
        modifier = modifier.padding(horizontal = Spacing.small)
    )
}
