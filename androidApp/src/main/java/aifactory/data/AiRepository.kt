package aifactory.data

import aifactory.ui.screens.dashboard.DashboardItem
import aifactory.ui.screens.dashboard.DashboardPage

interface AiRepository {
    @Deprecated("Use fetchDashboardPage instead")
    suspend fun fetchDashboard(): List<Any>
    suspend fun fetchSettings(): Map<String, Boolean>
    suspend fun login(user: String, pass: String): Boolean

    // Dashboard methods
    suspend fun fetchDashboardPage(cursor: String? = null, pageSize: Int = 10): DashboardPage
    suspend fun setFavorite(id: String, value: Boolean): Boolean
    suspend fun fetchDashboardItemById(id: String): DashboardItem?
}
