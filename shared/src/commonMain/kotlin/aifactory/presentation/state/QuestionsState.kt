package aifactory.presentation.state

import aifactory.domain.models.Question

data class QuestionsState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val items: List<Question> = emptyList(),
    val index: Int = 0,
)

