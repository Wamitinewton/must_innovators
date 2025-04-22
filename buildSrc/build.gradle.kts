plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    // Android Gradle Plugin
    implementation("com.android.tools.build:gradle:8.7.3")

    // Kotlin Plugins
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.0")
    implementation("org.jetbrains.kotlin:kotlin-serialization:2.0.0")
    implementation("org.jetbrains.kotlin.plugin.compose:org.jetbrains.kotlin.plugin.compose.gradle.plugin:2.0.0")

    // Hilt
    implementation("com.google.dagger:hilt-android-gradle-plugin:2.51")

    // Room
    implementation("androidx.room:room-gradle-plugin:2.6.1")

    // KSP
    implementation("com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:2.0.0-1.0.24")

    // Firebase
    implementation("com.google.gms:google-services:4.4.2")
}
