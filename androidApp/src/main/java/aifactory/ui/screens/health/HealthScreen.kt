package aifactory.ui.screens.health

import aifactory.core.health.HealthItem
import aifactory.ui.widgets.ContentView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HealthScreen(vm: HealthViewModel = viewModel()) {
    val report by vm.report.collectAsState()

    LaunchedEffect(Unit) {
        vm.run()
    }

    ContentView {
        Column {
            Text("Health Check Report (wireframe)")
            Spacer(modifier = Modifier.height(16.dp))

            if (report == null) {
                Text("Running checks...")
            } else {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(report!!.items) { item ->
                        HealthItemRow(item)
                    }
                }
            }
        }
    }
}

@Composable
private fun HealthItemRow(item: HealthItem) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text("[${item.status}] ${item.label}")
        item.detail?.let {
            Text("  - $it")
        }
    }
}
