package aifactory.ui.screens.about

import aifactory.core.AppInfo
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AboutScreen(
    onOpenDiagnostics: (() -> Unit)? = null,
    onOpenLicenses: (() -> Unit)? = null,
    appInfo: AppInfo,
    backupViewModel: BackupViewModel = viewModel()
) {
    val context = LocalContext.current

    val exportLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("application/zip"),
        onResult = { uri -> uri?.let { backupViewModel.performExportZip(it) } }
    )

    val importLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri -> uri?.let { backupViewModel.performImportZip(it) } }
    )

    LaunchedEffect(backupViewModel.events) {
        backupViewModel.events.collectLatest { event ->
            when (event) {
                is BackupViewModel.UiEvent.AskDestZip -> {
                    exportLauncher.launch(event.suggestedName)
                }
                is BackupViewModel.UiEvent.AskSrcZip -> {
                    importLauncher.launch(arrayOf("application/zip"))
                }
                is BackupViewModel.UiEvent.Toast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    Column {
        Text("App Name: ${appInfo.appName}")
        Text("Package: ${appInfo.packageName}")
        Text("Version: ${appInfo.versionName} (${appInfo.versionCode})")
        Text("Build Type: ${appInfo.buildType}")
        appInfo.flavor?.let { Text("Flavor: $it") }
        appInfo.dataDir?.let { Text("Data Directory: $it") }
        appInfo.androidVersion?.let { Text("Android Version: $it") }

        Spacer(modifier = Modifier.height(32.dp))

        onOpenDiagnostics?.let {
            Button(onClick = it) { Text("Diagnostics") }
        }
        onOpenLicenses?.let {
            Button(onClick = it) { Text("Licenses & Acknowledgments") }
        }

        Spacer(modifier = Modifier.height(32.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(32.dp))

        BackupSheet(
            vm = backupViewModel,
            onLaunchCreateDocument = { name, mime -> exportLauncher.launch(name) },
            onLaunchOpenDocument = { mime, _ -> importLauncher.launch(arrayOf(mime)) }
        )
    }
}
