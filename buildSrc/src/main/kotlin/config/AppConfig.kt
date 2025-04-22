package config

import versions.Versions

object AppConfig {
    const val applicationId = "com.newton.app"
    const val compileSdk = Versions.compileSdk
    const val minSdk = Versions.minSdk
    const val targetSdk = Versions.targetSdk
    const val jvmTarget = Versions.jvmTarget

    const val versionCode = 1
    const val versionName = "1.0.0"

    const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
}