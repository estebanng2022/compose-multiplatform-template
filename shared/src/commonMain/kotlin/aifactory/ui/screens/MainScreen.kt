package aifactory.ui.screens

import aifactory.navigation.AppNavigation
import aifactory.ui.widgets.background.AppBackground
import aifactory.ui.widgets.page.PageContainer
import aifactory.ui.widgets.molecules.PageHeader
import aifactory.ui.navigation.NavRailToggle
import aifactory.ui.navigation.SideNavigation
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    // State for the currently selected navigation item and the collapsed state of the nav rail
    var selectedId by remember { mutableStateOf(AppNavigation.mainNavItems.first().id) }
    var collapsed by remember { mutableStateOf(false) }

    AppBackground {
        Row(modifier = modifier) {
            SideNavigation(
                mainItems = AppNavigation.mainNavItems,
                bottomActionItems = AppNavigation.bottomNavActions,
                selectedId = selectedId,
                onItemSelected = {
                    when (it) {
                        "theme" -> {
                            // In a real app, you would toggle the theme here
                            // For this demo, let's just toggle the collapsed state for fun
                            collapsed = !collapsed
                        }
                        "logout" -> {
                            // Handle logout logic
                        }
                        else -> {
                            selectedId = it
                        }
                    }
                },
                collapsed = collapsed,
                header = {
                    // A placeholder for a logo or workspace switcher
                },
                footer = {
                    NavRailToggle(
                        isCollapsed = collapsed,
                        onToggle = { collapsed = !collapsed }
                    )
                }
            )

            // Main content area, wrapped in our PageContainer
            PageContainer {
                PageHeader(
                    title = selectedId.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() },
                    subtitle = "Welcome to the $selectedId screen."
                )
                // In a real app, you'd have a NavHost here to show different screen content
                // For this demo, we just show a simple text
                Text("Screen content for: $selectedId")
            }
        }
    }
}
