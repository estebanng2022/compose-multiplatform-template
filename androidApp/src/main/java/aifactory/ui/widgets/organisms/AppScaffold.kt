package aifactory.ui.widgets.organisms

import aifactory.core.flags.LocalFeatureFlags
import aifactory.navigation.AppNavGraph
import aifactory.navigation.sideNavItems
import aifactory.ui.foundation.TestTags
import aifactory.ui.foundation.UiStrings
import aifactory.ui.foundation.asButton
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlaylistPlay
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import aifactory.ui.theme.LocalSizing
import aifactory.ui.theme.LocalSpacing
import androidx.compose.foundation.layout.Arrangement

@Composable
fun AppScaffold(navController: NavHostController) {
    val flags = LocalFeatureFlags.current

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                SideNav(
                    currentRoute = currentRoute(navController),
                    onNavigate = { route ->
                        if (currentRoute(navController) != route) {
                            navController.navigate(route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )

                // Main Content Area
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(Color.Gray.copy(alpha = 0.1f))
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    AppNavGraph(navController = navController)
                }
            }

            DebugOverlay(enabled = flags.showDebugOverlay)
        }
    }
}

@Composable
private fun DebugOverlay(enabled: Boolean) {
    if (!enabled) return
    Box(Modifier.fillMaxSize()) {
        Text(
            "WIREFRAMES",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
                .background(Color.Black.copy(alpha = 0.6f), shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp),
            color = Color.White
        )
    }
}

@Composable
private fun SideNav(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .width(220.dp)
            .fillMaxHeight()
            .background(Color.Gray.copy(alpha = 0.2f))
            .padding(LocalSpacing.current.sm)
            .testTag(TestTags.SideNav)
            .semantics { contentDescription = UiStrings.sideNav }
    ) {
        Text("Side Nav", color = Color.White)
        Spacer(modifier = Modifier.height(LocalSpacing.current.md))
        sideNavItems.forEach { item ->
            val isSelected = currentRoute == item.route
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(TestTags.SideNav_Item + item.route)
                    .asButton(selected = isSelected)
                    .semantics { contentDescription = item.label }
                    .clickable { onNavigate(item.route) }
                    .background(if (isSelected) Color.Blue.copy(alpha = 0.3f) else Color.Transparent)
                    .padding(LocalSpacing.current.md)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(LocalSpacing.current.sm)
                ) {
                    Icon(
                        imageVector = routeIcon(item.route),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .width(LocalSizing.current.iconMd)
                            .height(LocalSizing.current.iconMd)
                    )
                    Text(item.label, color = Color.White)
                }
            }
        }
    }
}

@Composable
private fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

@Composable
private fun routeIcon(route: String) = when (route) {
    aifactory.navigation.Routes.DASHBOARD -> Icons.Filled.Dashboard
    aifactory.navigation.Routes.AGENTS -> Icons.Filled.Person
    aifactory.navigation.Routes.PIPELINES -> Icons.Filled.PlaylistPlay
    aifactory.navigation.Routes.RUNS -> Icons.Filled.List
    aifactory.navigation.Routes.WIZARD -> Icons.Filled.AutoAwesome
    aifactory.navigation.Routes.SHOWCASE -> Icons.Filled.AutoAwesome
    aifactory.navigation.Routes.SETTINGS -> Icons.Filled.Settings
    aifactory.navigation.Routes.ABOUT -> Icons.Filled.Info
    else -> Icons.Filled.Dashboard
}
