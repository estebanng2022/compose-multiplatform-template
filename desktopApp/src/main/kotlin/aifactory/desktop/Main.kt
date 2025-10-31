package aifactory.desktop

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import aifactory.App

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "AiFactory") {
        App()
    }
}
