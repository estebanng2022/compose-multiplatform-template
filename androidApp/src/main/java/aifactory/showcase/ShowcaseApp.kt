package aifactory.showcase

import aifactory.ai.AgentExecutor
import aifactory.ai.LocalAIManager
import aifactory.ai.RuleEngine
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Divider
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun ShowcaseApp(
    modifier: Modifier = Modifier,
    localAiManager: LocalAIManager = remember { LocalAIManager.default() },
    agentExecutor: AgentExecutor = remember { AgentExecutor() },
    ruleEngine: RuleEngine = remember(localAiManager, agentExecutor) {
        RuleEngine(localAiManager = localAiManager, agentExecutor = agentExecutor)
    },
) {
    val navController = rememberNavController()
    val destinations = remember { ShowcaseDestination.values() }
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route ?: ShowcaseDestination.Theme.route
    val selectedIndex = destinations.indexOfFirst { it.route == currentRoute }.takeIf { it >= 0 } ?: 0

    Column(modifier = modifier.fillMaxSize()) {
        ScrollableTabRow(selectedTabIndex = selectedIndex) {
            destinations.forEachIndexed { index, destination ->
                Tab(
                    selected = index == selectedIndex,
                    onClick = {
                        if (currentRoute != destination.route) {
                            navController.navigate(destination.route) {
                                launchSingleTop = true
                                restoreState = true
                                popUpTo(ShowcaseDestination.Theme.route) {
                                    inclusive = false
                                    saveState = true
                                }
                            }
                        }
                    },
                    text = { Text(destination.label) },
                )
            }
        }
        Divider()
        Box(modifier = Modifier.fillMaxSize()) {
            ShowcaseNavHost(
                navController = navController,
                localAiManager = localAiManager,
                ruleEngine = ruleEngine,
                startDestination = ShowcaseDestination.Theme,
            )
        }
    }
}

