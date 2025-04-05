plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    id("com.google.dagger.hilt.android") version "2.51" apply false
    id("androidx.room") version "2.6.1" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.0"
    alias(libs.plugins.android.library) apply false
    id("com.google.devtools.ksp") version "2.0.0-1.0.24" apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
    alias(libs.plugins.ktlint)
}

allprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    ktlint {
        android.set(true)
        verbose.set(true)
        baseline.set(file("baseline.xml"))
        filter {
            exclude { element -> element.file.path.contains("generated/") }
        }
    }
}
