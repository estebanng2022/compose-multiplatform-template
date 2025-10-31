import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

repositories {
    mavenCentral()
    google()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(project(":shared"))
    implementation("org.slf4j:slf4j-api:2.0.17")
    runtimeOnly("ch.qos.logback:logback-classic:1.5.20")
}

kotlin {
    jvmToolchain(17)
}

compose.desktop {
    application {
        // Entry point is the top-level function in desktopApp/src/jvmMain/kotlin/main.kt
        // which compiles to the default package class name "MainKt"
        mainClass = "MainKt"

        nativeDistributions {
            packageVersion = "1.0.0"
            targetFormats(
                TargetFormat.Dmg,
                TargetFormat.Msi,
                TargetFormat.Deb,
                TargetFormat.Exe,
                TargetFormat.Rpm
            )
            packageName = "AiFactory"
            description = "AiFactory Desktop"
            // macOS { setIconFile("ic.icns") }
            // Windows { setIconFile("ic.ico") }
            // Linux { setIconFile("ic.png") }
        }
    }
}
