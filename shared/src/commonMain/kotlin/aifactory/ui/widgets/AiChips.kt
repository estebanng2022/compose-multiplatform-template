package aifactory.ui.widgets

import aifactory.ui.foundation.Spacing
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Premium chip system consistent with Material3.
 * Includes: Assist, Filter (multi), Choice (single), InputChip with clear
 * and reusable groups (single/multi).
 */

// ===== API Models =====

data class ChipItem(
    val id: String,
    val label: String,
    val enabled: Boolean = true,
    val leading: (@Composable () -> Unit)? = null,
)

// ===== Base Helpers =====

private val DefaultChipPadding = PaddingValues(horizontal = Spacing.medium, vertical = Spacing.xSmall)
private val DefaultChipShape: Shape @Composable get() = RoundedCornerShape(999.dp)

@Composable
private fun outlineBorder(selected: Boolean): BorderStroke? =
    if (selected) BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.25f)) else null

// ===== AssistChip (suggestions) =====

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiAssistChip(
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit,
    leading: (@Composable () -> Unit)? = null,
) {
    AssistChip(
        onClick = onClick,
        enabled = enabled,
        leadingIcon = leading,
        label = { Text(text = label, style = MaterialTheme.typography.labelLarge) },
        modifier = modifier,
        colors = AssistChipDefaults.assistChipColors(
            containerColor = MaterialTheme.colorScheme.surface,
            labelColor = MaterialTheme.colorScheme.onSurface,
        ),
        shape = DefaultChipShape,
    )
}

// ===== FilterChip (multi-select) =====

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiFilterChip(
    label: String,
    selected: Boolean,
    onSelectedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leading: (@Composable () -> Unit)? = null,
) {
    val container by animateColorAsState(
        if (selected) MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
        else MaterialTheme.colorScheme.surface,
        label = "chipContainer"
    )

    FilterChip(
        selected = selected,
        onClick = { onSelectedChange(!selected) },
        enabled = enabled,
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
            )
        },
        leadingIcon = leading,
        modifier = modifier,
        colors = FilterChipDefaults.filterChipColors(
            containerColor = container,
            labelColor = MaterialTheme.colorScheme.onSurface,
            selectedContainerColor = container,
            selectedLabelColor = MaterialTheme.colorScheme.onSurface,
        ),
        shape = DefaultChipShape,
    )
}

// ===== ChoiceChip (single-select) =====

@Composable
fun AiChoiceChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leading: (@Composable () -> Unit)? = null,
) {
    val container by animateColorAsState(
        if (selected) MaterialTheme.colorScheme.secondaryContainer
        else MaterialTheme.colorScheme.surface,
        label = "choiceBg"
    )
    Surface(
        onClick = onClick,
        enabled = enabled,
        shape = DefaultChipShape,
        color = container,
        border = outlineBorder(selected),
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(DefaultChipPadding)
        ) {
            if (leading != null) {
                leading()
                Spacer(Modifier.width(Spacing.small))
            }
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
                color = if (selected) MaterialTheme.colorScheme.onSecondaryContainer
                else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

// ===== InputChip (with clear) =====

@Composable
fun AiInputChip(
    label: String,
    onClear: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leading: (@Composable () -> Unit)? = null,
) {
    Surface(
        shape = DefaultChipShape,
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        modifier = modifier,
        tonalElevation = 0.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(DefaultChipPadding)
        ) {
            if (leading != null) { leading(); Spacer(Modifier.width(Spacing.small)) }
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.width(Spacing.xSmall))
            IconButton(onClick = onClear, enabled = enabled, modifier = Modifier.size(20.dp)) {
                Icon(Icons.Outlined.Close, contentDescription = "Clear", modifier = Modifier.size(18.dp))
            }
        }
    }
}

// ===== Groups =====

/** Single-selection group (Choice). */
@Composable
fun AiChipGroupSingle(
    items: List<ChipItem>,
    selectedId: String?,
    onSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    horizontalSpacing: Dp = Spacing.small,
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(horizontalSpacing)) {
        items.forEach { item ->
            AiChoiceChip(
                label = item.label,
                selected = item.id == selectedId,
                enabled = item.enabled,
                leading = item.leading,
                onClick = { onSelected(item.id) }
            )
        }
    }
}

/** Multi-selection group (Filter). */
@Composable
fun AiChipGroupMulti(
    items: List<ChipItem>,
    selectedIds: Set<String>,
    onSelectedChange: (Set<String>) -> Unit,
    modifier: Modifier = Modifier,
    horizontalSpacing: Dp = Spacing.small,
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(horizontalSpacing)) {
        items.forEach { item ->
            val isSelected = selectedIds.contains(item.id)
            AiFilterChip(
                label = item.label,
                selected = isSelected,
                enabled = item.enabled,
                leading = item.leading,
                onSelectedChange = { now ->
                    val next = selectedIds.toMutableSet()
                    if (now) next.add(item.id) else next.remove(item.id)
                    onSelectedChange(next)
                }
            )
        }
    }
}
