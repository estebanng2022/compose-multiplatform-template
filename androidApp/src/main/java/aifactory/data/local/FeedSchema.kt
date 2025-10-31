package aifactory.data.local

import aifactory.ui.screens.dashboard.DashboardItem
import kotlinx.serialization.Serializable

@Serializable
data class FeedSnapshotV1(
    val schema: Int = 1,
    val savedAtMillis: Long,
    val ttlMillis: Long,
    val nextCursor: String?,
    val items: List<DashboardItem>
)
