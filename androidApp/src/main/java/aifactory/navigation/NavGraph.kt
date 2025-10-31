package aifactory.navigation

import aifactory.core.AppInfoProvider
import aifactory.core.Logx
import aifactory.ui.screens.about.AboutScreen
import aifactory.ui.screens.dashboard.DashboardAction
import aifactory.ui.screens.dashboard.DashboardList
import aifactory.ui.screens.dashboard.DashboardViewModel
import aifactory.ui.screens.detail.DetailScreen
import aifactory.ui.screens.detail.DetailViewModel
import aifactory.ui.screens.health.HealthScreen
import aifactory.ui.screens.login.LoginViewModel
import aifactory.ui.screens.settings.SettingsViewModel
import aifactory.ui.screens.pipelines.PipelinesScreen
import aifactory.ui.screens.runs.RunsScreen
import aifactory.ui.screens.settings.SettingsScreen
import aifactory.di.PresentationProviders
import aifactory.ui.screens.agents.AgentsScreen
import aifactory.ui.widgets.molecules.ContentView
import aifactory.ui.widgets.states.RenderAppState
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.height
import androidx.compose.ui.unit.dp
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import aifactory.showcase.ShowcaseApp
import aifactory.ui.screens.wizard.ProjectWizardScreen
import aifactory.ui.screens.questions.QuestionsListScreen

@Composable
fun AppNavGraph(
    navController: NavHostController,
    startDestination: String = Routes.DASHBOARD
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Routes.LOGIN) {
            LoginScreen()
        }
        composable(Routes.DASHBOARD) {
            DashboardScreen(navController = navController)
        }
        composable(Routes.SETTINGS) {
            SettingsScreen()
        }
        composable(Routes.WIZARD) {
            ContentView {
                ProjectWizardScreen(
                    onClose = { navController.popBackStack() },
                    onOpenSettings = { navController.navigate(Routes.SETTINGS) }
                )
            }
        }
        composable(Routes.QUESTIONS) {
            ContentView { QuestionsListScreen() }
        }
        composable(Routes.SHOWCASE) {
            ContentView {
                ShowcaseApp()
            }
        }
        composable(Routes.AGENTS) {
            val context = LocalContext.current
            val vm = remember { PresentationProviders.agentsViewModel(context) }
            AgentsScreen(viewModel = vm, configPath = aifactory.core.Config.Paths.AGENTS_DIR)
        }
        composable(Routes.PIPELINES) {
            val context = LocalContext.current
            val vm = remember { PresentationProviders.pipelinesViewModel(context) }
            PipelinesScreen(viewModel = vm)
        }
        composable(Routes.RUNS) {
            val context = LocalContext.current
            val vm = remember { PresentationProviders.runsViewModel(context) }
            // For demo, show runs for a placeholder pipeline id; in real flow, pass argument
            RunsScreen(viewModel = vm, pipelineId = "pipeline-demo")
        }
        composable(Routes.ABOUT) {
            val context = LocalContext.current
            val info = remember { AppInfoProvider.from(context) }
            ContentView {
                AboutScreen(
                    appInfo = info,
                    onOpenDiagnostics = { navController.navigate(Routes.HEALTH) },
                    onOpenLicenses = { navController.navigate(Routes.LICENSES) }
                )
            }
        }
        composable(Routes.HEALTH) {
            HealthScreen()
        }
        composable(Routes.LICENSES) {
            ContentView { 
                Text("Licenses Screen (placeholder)")
            }
        }
        composable(
            route = Routes.DETAIL_WITH_ARG,
            arguments = listOf(navArgument("id") { type = NavType.StringType }),
            deepLinks = listOf(navDeepLink { uriPattern = Routes.DETAIL_DEEPLINK })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")!!
            val vm: DetailViewModel = viewModel()
            val ui by vm.state.collectAsState()
            val context = LocalContext.current

            ContentView {
                RenderAppState(state = ui, onRetry = { vm.refresh() }) { item ->
                    DetailScreen(
                        item = item,
                        onBack = { navController.popBackStack() },
                        onShare = { payload ->
                            val intent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_TEXT, payload)
                            }
                            context.startActivity(Intent.createChooser(intent, "Share via"))
                        },
                        onToggleFavorite = { vm.toggleFavorite(it) }
                    )
                }
            }
        }
    }
}

// --- Screen Implementations ---

@Composable
fun LoginScreen() {
    val vm: LoginViewModel = viewModel()
    val ui by vm.state.collectAsState()

    ContentView {
        Column {
            Text("Login Screen (wireframe)")
            RenderAppState(state = ui, onRetry = null) { 
                Text("Login Successful!")
            }
            Button(onClick = { vm.submit("user", "pass") }) {
                Text("Login")
            }
        }
    }
}

@Composable
fun DashboardScreen(navController: NavHostController) {
    val vm: DashboardViewModel = viewModel()
    val ui by vm.state.collectAsState()
    val isLoadingMore by vm.isLoadingMore.collectAsState()
    val canLoadMore by vm.canLoadMore.collectAsState()

    // Dummy event handling for opening detail screen
    // In a real app, this would use a more robust single-event mechanism (Channel, etc.)
    LaunchedEffect(vm) {
        // This is a simplified listener. In a real app, use a SharedFlow or Channel for one-time events.
    }

    ContentView {
        Column {
            Button(onClick = { vm.dispatch(DashboardAction.Refresh) }) {
                Text("Refresh")
            }
            Button(
                onClick = { navController.navigate(Routes.WIZARD) },
                modifier = Modifier
                    .height(48.dp)
                    .semantics { contentDescription = "Nuevo Proyecto (Wizard)" }
            ) { Text("Nuevo Proyecto") }
            RenderAppState(state = ui, onRetry = { vm.dispatch(DashboardAction.Refresh) }) { items ->
                DashboardList(
                    items = items,
                    isLoadingMore = isLoadingMore,
                    canLoadMore = canLoadMore,
                    onLoadMore = { vm.dispatch(DashboardAction.LoadMore) },
                    onToggleFavorite = { id, value -> vm.dispatch(DashboardAction.ToggleFavorite(id, value)) },
                    onOpen = { id -> navController.navigate("${Routes.DETAIL}/$id") }
                )
            }
        }
    }
}

@Composable
fun SettingsScreen() {
    val vm: SettingsViewModel = viewModel()
    val ui by vm.state.collectAsState()

    ContentView {
        Column {
            Text("Settings Screen (wireframe)")
            RenderAppState(state = ui, onRetry = { vm.refresh() }) { data ->
                Column {
                    data.forEach { (key, value) ->
                        Row {
                            Text(key)
                            Switch(checked = value, onCheckedChange = { vm.toggle(key, it) })
                        }
                    }
                }
            }
        }
    }
}
