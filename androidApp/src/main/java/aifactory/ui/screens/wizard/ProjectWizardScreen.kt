package aifactory.ui.screens.wizard

import aifactory.navigation.Routes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@Composable
fun ProjectWizardScreen(onClose: () -> Unit, onOpenSettings: () -> Unit) {
    Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Wizard de Proyecto", style = MaterialTheme.typography.titleLarge)
        Text("Paso 1: Theme (solo selector)", style = MaterialTheme.typography.titleMedium)
        ProjectWizardThemeStep(
            projectId = "demo",
            onOpenSettings = onOpenSettings,
            onNext = { /* continuar al siguiente paso si existiera */ },
        )
        Button(
            onClick = onClose,
            modifier = Modifier.semantics { contentDescription = "Cerrar Wizard" }
        ) { Text("Cerrar") }
    }
}
