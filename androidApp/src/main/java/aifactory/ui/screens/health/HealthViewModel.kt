package aifactory.ui.screens.health

import aifactory.core.health.HealthCheck
import aifactory.core.health.HealthReport
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HealthViewModel(application: Application) : AndroidViewModel(application) {

    private val _report = MutableStateFlow<HealthReport?>(null)
    val report: StateFlow<HealthReport?> = _report

    fun run() {
        viewModelScope.launch {
            _report.value = HealthCheck.run(getApplication())
        }
    }
}
