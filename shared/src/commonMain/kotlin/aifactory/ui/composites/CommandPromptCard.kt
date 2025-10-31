package aifactory.ui.composites

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import aifactory.ui.widgets.buttons.AiIconButton
import aifactory.ui.widgets.AiChipGroupSingle
import aifactory.ui.widgets.ChipItem
import aifactory.ui.foundation.Sizing
import aifactory.ui.foundation.Spacing
import aifactory.ui.widgets.inputs.CommandTextField

@Composable
fun CommandPromptCard(
    title: String,
    subtitle: String? = null,
    placeholder: String = "Type your idea and we\'ll bring it to life (or /command)",
    suggestions: List<String> = emptyList(),
    onSubmit: (String) -> Unit,
    onAttach: (() -> Unit)? = null,
    onVoice:  (() -> Unit)? = null,
) {
    var text by remember { mutableStateOf("") }

    Card(
        modifier = Modifier.widthIn(max = 720.dp), // Dejado como valor específico
        shape = MaterialTheme.shapes.extraLarge,
        elevation = CardDefaults.cardElevation(defaultElevation = Spacing.xSmall / 2)
    ) {
        Column(Modifier.padding(Spacing.xxLarge)) {
            Text(title, style = MaterialTheme.typography.headlineLarge)
            if (!subtitle.isNullOrBlank()) {
                Spacer(Modifier.height(Spacing.small))
                Text(subtitle, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }

            Spacer(Modifier.height(Spacing.xxLarge))

            CommandTextField(
                value = text,
                onValueChange = { text = it },
                placeholder = placeholder,
                onSubmit = { if (text.isNotBlank()) onSubmit(text) },
                leadingIcon = {
                    Row {
                        if (onAttach != null) {
                            AiIconButton(onClick = onAttach) { Icon(Icons.Default.AttachFile, "Attach") }
                        }
                        if (onVoice != null) {
                            AiIconButton(onClick = onVoice) { Icon(Icons.Default.Mic, "Voice") }
                        }
                    }
                }
            )

            Spacer(Modifier.height(Spacing.medium))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom
            ) {
                if (suggestions.isNotEmpty()) {
                    val suggestionItems = suggestions.map { ChipItem(id = it, label = it) }
                    AiChipGroupSingle(
                        items = suggestionItems,
                        selectedId = null, // Suggestions are one-off actions, not selections
                        onSelected = { chipId -> text = chipId } // Set text field value on click
                    )
                }
                Spacer(Modifier.weight(1f))
                FilledTonalButton(
                    onClick = { if (text.isNotBlank()) onSubmit(text) },
                    enabled = text.isNotBlank(),
                    modifier = Modifier.height(Sizing.largeButtonHeight) // Usando el token
                ) {
                    Icon(Icons.Default.Send, contentDescription = null)
                    Spacer(Modifier.width(Spacing.small))
                    Text("Generate")
                }
            }
        }
    }
}
