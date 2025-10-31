package aifactory.navigation

/**
 * A simple data model for a navigation item in the SideNav.
 */
data class NavItem(val route: String, val label: String)

/**
 * The list of items to be displayed in the SideNav.
 */
val sideNavItems = listOf(
    NavItem(Routes.DASHBOARD, "Dashboard"),
    NavItem(Routes.SHOWCASE, "Showcase"),
    NavItem(Routes.WIZARD, "Wizard"),
    NavItem(Routes.QUESTIONS, "Questions"),
    NavItem(Routes.AGENTS, "Agents"),
    NavItem(Routes.PIPELINES, "Pipelines"),
    NavItem(Routes.RUNS, "Runs"),
    NavItem(Routes.SETTINGS, "Settings"),
    NavItem(Routes.ABOUT, "About")
)
