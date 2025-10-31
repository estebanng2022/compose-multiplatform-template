package aifactory.ui.screens.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun BackupSheet(
    vm: BackupViewModel = viewModel(),
    onLaunchCreateDocument: (suggestedName: String, mime: String) -> Unit,
    onLaunchOpenDocument: (mime: String, multiple: Boolean) -> Unit
) {
    val busy by vm.busy.collectAsState()

    Column {
        Button(onClick = { vm.requestExportZip() }, enabled = !busy) {
            Text("Export ZIP")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { vm.requestImportZip() }, enabled = !busy) {
            Text("Import ZIP")
        }

        if (busy) {
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator()
            Text("Processing...")
        }
    }
}
