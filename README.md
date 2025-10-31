[![Obsolete project](https://jb.gg/badges/obsolete-plastic.svg)](https://github.com/JetBrains#jetbrains-on-github)
[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
# [Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform) application

This template has been archived.

* To create Compose Multiplatform projects, use the [Kotlin Multiplatform wizard](https://kmp.jetbrains.com/).
  Make sure to enable the **Share UI** option.
* To learn how to build Compose Multiplatform projects, see the [Get started with Compose Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-multiplatform-getting-started.html) tutorial.

  If you have an idea on how to improve the tutorial, create a pull request to the [documentation repository](https://github.com/JetBrains/kotlin-multiplatform-dev-docs).

## Build Variants & Flags

### Android
- `wireframesDebug` → UI with placeholders, mock data, overlay
- `prodDebug` → Normal UI, without mocks or overlay
- `prodRelease` → Optimized release build (requires signing configuration)

#### Android Flags (via BuildConfig)
- `FEATURE_WIREFRAME` (wireframes=true, prod=false)
- `USE_MOCK_DATA` (wireframes=true, prod=false)
- `SHOW_DEBUG_OVERLAY` (wireframes=true, prod=false)

#### Android Commands
- Install wireframes debug: `./gradlew :app:installWireframesDebug`
- Install production debug: `./gradlew :app:installProdDebug`
- Assemble production release: `./gradlew :app:assembleProdRelease`

### Desktop (Compose)
- Run (prod-like): `./gradlew :desktopApp:run`
- Run (wireframes ON): `./gradlew :desktopApp:run -PjvmArgs="-DFEATURE_WIREFRAME=true -DUSE_MOCK_DATA=true -DSHOW_DEBUG_OVERLAY=true"`
- Package (current OS): `./gradlew :desktopApp:createDistributable`
- Outputs: `desktopApp/build/compose/binaries/main/...`
