package aifactory.ui.widgets.chips

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import aifactory.ui.components.AiAssistChip

@Deprecated(
    message = "Use AiChipGroupSingle with AssistChips instead for better consistency.",
    replaceWith = ReplaceWith(
        "AiChipGroupSingle(items = suggestions.map { ChipItem(id = it, label = it) }, selectedId = null, onSelected = onSuggestionClick)",
        "aifactory.ui.components.chips.AiChipGroupSingle",
        "aifactory.ui.components.chips.ChipItem"
    )
)
@Composable
fun SuggestionChipRow(
    suggestions: List<String>,
    onSuggestionClick: (String) -> Unit
) {
    Row(
        modifier = Modifier.wrapContentWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        suggestions.forEach { suggestion ->
            AiAssistChip(
                label = suggestion,
                onClick = { onSuggestionClick(suggestion) }
            )
        }
    }
}
