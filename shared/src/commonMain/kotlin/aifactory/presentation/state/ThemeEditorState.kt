package aifactory.presentation.state

import aifactory.domain.models.Theme

data class ThemeEditorState(
    val theme: Theme? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val validationIssues: List<String> = emptyList(),
)

