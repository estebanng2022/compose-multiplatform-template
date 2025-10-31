package aifactory.showcase

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import aifactory.ai.LocalAIManager
import aifactory.ai.RuleEngine
import aifactory.showcase.screens.AIAgentMonitorScreen
import aifactory.showcase.screens.ComponentsGalleryScreen
import aifactory.showcase.screens.PipelineDemoScreen
import aifactory.showcase.screens.ThemeGalleryScreen

enum class ShowcaseDestination(val route: String, val label: String) {
    Theme("showcase/theme", "Themes"),
    Components("showcase/components", "Components"),
    Pipeline("showcase/pipeline", "Pipeline"),
    Agents("showcase/agents", "AI Agents");
}

@Composable
fun ShowcaseNavHost(
    navController: NavHostController,
    startDestination: ShowcaseDestination = ShowcaseDestination.Theme,
    localAiManager: LocalAIManager,
    ruleEngine: RuleEngine,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.route,
    ) {
        showcaseGraph(localAiManager = localAiManager, ruleEngine = ruleEngine)
    }
}

private fun NavGraphBuilder.showcaseGraph(
    localAiManager: LocalAIManager,
    ruleEngine: RuleEngine,
) {
    composable(ShowcaseDestination.Theme.route) {
        ThemeGalleryScreen()
    }
    composable(ShowcaseDestination.Components.route) {
        ComponentsGalleryScreen()
    }
    composable(ShowcaseDestination.Pipeline.route) {
        PipelineDemoScreen(ruleEngine = ruleEngine)
    }
    composable(ShowcaseDestination.Agents.route) {
        AIAgentMonitorScreen(localAiManager = localAiManager)
    }
}

