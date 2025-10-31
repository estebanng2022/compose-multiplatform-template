package aifactory.ui.screens.dashboard

/**
 * Defines all possible user actions that can be dispatched from the Dashboard screen.
 */
sealed interface DashboardAction {
    data object Refresh : DashboardAction
    data object LoadMore : DashboardAction
    data class ToggleFavorite(val id: String, val value: Boolean) : DashboardAction
    data class Open(val id: String) : DashboardAction
}
