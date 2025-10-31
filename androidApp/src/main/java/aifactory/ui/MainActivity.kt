package aifactory.ui

import aifactory.core.flags.BuildConfigFeatureFlags
import aifactory.core.flags.LocalFeatureFlags
import aifactory.ui.widgets.organisms.AppScaffold
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import aifactory.ui.theme.AiFactoryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val flags = BuildConfigFeatureFlags()
        setContent {
            CompositionLocalProvider(LocalFeatureFlags provides flags) {
                val navController = rememberNavController()
                AiFactoryTheme {
                    Surface(color = Color.Transparent) {
                        AppScaffold(navController = navController)
                    }
                }
            }
        }
    }
}
