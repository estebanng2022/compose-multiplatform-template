plugins {
    kotlin("multiplatform")
    id("com.android.application")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("io.sentry.android.gradle") version "4.9.0"
}

kotlin {
    androidTarget()
    jvmToolchain(17)
    sourceSets {
        val androidMain by getting {
            dependencies {
                implementation(project(":shared"))
                implementation(platform("androidx.compose:compose-bom:2024.10.01"))
                implementation("androidx.compose.ui:ui")
                implementation("androidx.compose.material3:material3")
                implementation("androidx.activity:activity-compose:1.9.3")
                implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.6")
                implementation("androidx.navigation:navigation-compose:2.8.3")
                implementation("androidx.compose.ui:ui-tooling-preview")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.5.0")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")
            }
        }
    }
}

sentry {
    autoUploadProguardMapping.set(true)
    includeProguardMapping.set(true)
    uploadNativeSymbols.set(false)
    org.set(providers.environmentVariable("SENTRY_ORG"))
    projectName.set(providers.environmentVariable("SENTRY_PROJECT_ANDROID"))
    authToken.set(providers.environmentVariable("SENTRY_AUTH_TOKEN"))
}

android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    namespace = "com.myapplication"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    buildFeatures {
        buildConfig = true
        compose = true
    }
    flavorDimensions += "mode"

    productFlavors {
        create("prod") {
            dimension = "mode"
            buildConfigField("Boolean", "FEATURE_WIREFRAME", "false")
            buildConfigField("Boolean", "USE_MOCK_DATA", "false")
            buildConfigField("Boolean", "SHOW_DEBUG_OVERLAY", "false")
        }
        create("wireframes") {
            dimension = "mode"
            applicationIdSuffix = ".wire"
            versionNameSuffix = "-wire"
            buildConfigField("Boolean", "FEATURE_WIREFRAME", "true")
            buildConfigField("Boolean", "USE_MOCK_DATA", "true")
            buildConfigField("Boolean", "SHOW_DEBUG_OVERLAY", "true")
        }
    }

    defaultConfig {
        applicationId = "com.myapplication.MyApplication"
        minSdk = (findProperty("android.minSdk") as String).toInt()
        targetSdk = (findProperty("android.targetSdk") as String).toInt()
        versionCode = 1
        versionName = "1.0"
        val sentryDsn = System.getenv("SENTRY_DSN") ?: ""
        buildConfigField("String", "SENTRY_DSN", "\"$sentryDsn\"")
        buildConfigField("boolean", "ENABLE_SENTRY", "false")
    }

    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"
            buildConfigField("String", "API_BASE_URL", "\"https://api-dev.example.com\"")
            resValue("string", "app_name", "AiFactory (Dev)")
        }
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            buildConfigField("String", "API_BASE_URL", "\"https://api.example.com\"")
            resValue("string", "app_name", "AiFactory")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
}
