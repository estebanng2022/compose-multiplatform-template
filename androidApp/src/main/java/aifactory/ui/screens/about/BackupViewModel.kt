package aifactory.ui.screens.about

import aifactory.data.local.BackupManager
import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class BackupViewModel(private val app: Application) : AndroidViewModel(app) {

    sealed interface UiEvent {
        data class Toast(val message: String): UiEvent
        data class AskDestZip(val suggestedName: String): UiEvent
        data object AskSrcZip: UiEvent
    }

    private val _busy = MutableStateFlow(false)
    val busy = _busy.asStateFlow()

    private val _events = MutableSharedFlow<UiEvent>()
    val events = _events.asSharedFlow()

    fun requestExportZip() {
        viewModelScope.launch {
            val timestamp = SimpleDateFormat("yyyyMMdd", Locale.US).format(Date())
            _events.emit(UiEvent.AskDestZip("aifactory-backup-$timestamp.zip"))
        }
    }

    fun performExportZip(dest: Uri) {
        viewModelScope.launch {
            _busy.value = true
            val result = BackupManager.exportAllToZip(app.applicationContext, dest)
            _events.emit(UiEvent.Toast(result.message ?: "Export finished."))
            _busy.value = false
        }
    }

    fun requestImportZip() {
        viewModelScope.launch {
            _events.emit(UiEvent.AskSrcZip)
        }
    }

    fun performImportZip(src: Uri) {
        viewModelScope.launch {
            _busy.value = true
            val result = BackupManager.importAllFromZip(app.applicationContext, src)
            _events.emit(UiEvent.Toast(result.message ?: "Import finished."))
            _busy.value = false
        }
    }
}
