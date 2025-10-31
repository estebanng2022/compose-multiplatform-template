package aifactory.ui.screens.questions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun QuestionsListScreen(questions: List<QuestionItem> = defaultQuestions()) {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Questions (placeholder)")
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(questions) { q ->
                ListItem(headlineContent = { Text(q.text) }, supportingContent = { Text(q.category) })
                Divider()
            }
        }
    }
}

data class QuestionItem(val id: String, val text: String, val category: String, val required: Boolean)

private fun defaultQuestions(): List<QuestionItem> = listOf(
    QuestionItem("q1", "¿La pantalla cumple el objetivo definido?", "objetivo", true),
    QuestionItem("q2", "¿Usa correctamente el layout y los widgets del Design System?", "diseño", true),
    QuestionItem("q3", "¿El flujo de datos respeta las capas (Presentation + Domain + Data)?", "arquitectura", true),
    QuestionItem("q4", "¿Tiene estados claros (loading, empty, error)?", "estados", true),
    QuestionItem("q5", "¿Cumple reglas de accesibilidad y theme?", "a11y", true),
    QuestionItem("q6", "¿Se puede reutilizar o mejorar algo?", "mejora", false),
)

