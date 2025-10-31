package aifactory.navigation

import aifactory.ui.screens.wizard.ProjectWizardThemeStep
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

object WizardRoutes {
    const val THEME = "wizard/theme/{projectId}"
}

@Composable
fun WizardNavGraph(navController: NavHostController, startProjectId: String) {
    NavHost(navController, startDestination = WizardRoutes.THEME.replace("{projectId}", startProjectId)) {
        composable(WizardRoutes.THEME) { backStack ->
            val projectId = backStack.arguments?.getString("projectId") ?: "demo"
            ProjectWizardThemeStep(
                projectId = projectId,
                onOpenSettings = { navController.navigate(Routes.SETTINGS) },
                onNext = { /* Could navigate to next step */ },
            )
        }
    }
}

