plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    kotlin("kapt") version "1.9.0"
    id("com.google.dagger.hilt.android") version "2.51" apply false
    id("androidx.room") version "2.6.1" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.0"
}