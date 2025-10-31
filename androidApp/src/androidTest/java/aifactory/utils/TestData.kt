package aifactory.utils

import aifactory.ui.screens.dashboard.DashboardItem

object TestData {

    val sampleDashboardItems = (0..20).map { i ->
        DashboardItem(
            id = "test_id_$i",
            title = "Test Item $i",
            subtitle = "Subtitle for test item $i",
            isFavorite = false
        )
    }

    val singleDashboardItem = DashboardItem(
        id = "single_item",
        title = "A Single Item",
        subtitle = "For detail view testing",
        isFavorite = true
    )
}
