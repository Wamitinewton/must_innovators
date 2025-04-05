plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-parcelize")
    id("kotlinx-serialization")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.newton.communities"
    compileSdk = 35

    defaultConfig {
        minSdk = 28

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.text.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Hilt
    implementation(Dependencies.hiltAndroid)
    ksp(Dependencies.hiltCompiler)

    // navigation
    implementation(Dependencies.hiltNavigation)
    implementation(Dependencies.composeNavigation)

    implementation(Dependencies.retrofit)
    implementation(Dependencies.retrofit2Converter)
    implementation(Dependencies.gsonCoverter)
    implementation(Dependencies.kotlinxSerialization)

    implementation(Dependencies.roomKtx)
    ksp(Dependencies.roomCompiler)
    implementation(Dependencies.roomRuntime)

    implementation(Dependencies.coilCompose)
    implementation(Dependencies.coilNetwork)

    implementation(Dependencies.extendedIcons)

    implementation(Dependencies.timber)

    implementation(project(":core"))
    implementation(project(":shared-ui"))
    implementation(project(":database"))
    implementation(project(":navigation"))
}
