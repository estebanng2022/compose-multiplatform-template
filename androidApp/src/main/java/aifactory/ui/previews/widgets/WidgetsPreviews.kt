package aifactory.ui.previews.widgets

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import aifactory.ui.widgets.organisms.AppScaffold
import aifactory.ui.widgets.molecules.ContentView
import aifactory.ui.widgets.molecules.EmptyStateWithAction

@Preview
@Composable
fun AppScaffold_Preview() {
    val navController = rememberNavController()
    AppScaffold(navController = navController)
}

@Preview
@Composable
fun ContentView_Preview() {
    ContentView { }
}

@Preview
@Composable
fun EmptyStateWithAction_Preview() {
    EmptyStateWithAction(message = "Nada aquí", actionLabel = "Crear") {}
}
