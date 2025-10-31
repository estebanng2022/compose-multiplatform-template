package aifactory.core

import com.myapplication.BuildConfig
import android.content.Context
import android.os.Build


data class AppInfo(
    val appName: String,
    val packageName: String,
    val versionName: String,
    val versionCode: Long,
    val buildType: String, // debug/release
    val flavor: String?, // if you use productFlavors; otherwise, null
    val dataDir: String?, // Paths.appDataDir(...)
    val androidVersion: String? // Build.VERSION.RELEASE (optional)
)

object AppInfoProvider {
    fun from(context: Context): AppInfo {
        val packageManager = context.packageManager
        val packageName = context.packageName
        val packageInfo = packageManager.getPackageInfo(packageName, 0)

        return AppInfo(
            appName = context.applicationInfo.loadLabel(packageManager).toString(),
            packageName = packageName,
            versionName = packageInfo.versionName ?: "1.0.0",
            versionCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                packageInfo.longVersionCode
            } else {
                @Suppress("DEPRECATION")
                packageInfo.versionCode.toLong()
            },
            buildType = BuildConfig.BUILD_TYPE,
            flavor = if (BuildConfig.FLAVOR.isNotEmpty()) BuildConfig.FLAVOR else null,
            dataDir = Paths.appDataDir(context).absolutePath,
            androidVersion = Build.VERSION.RELEASE
        )
    }
}
