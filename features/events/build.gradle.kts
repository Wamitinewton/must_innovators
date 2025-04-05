/**
 * Copyright (c) 2025 Meru Science Innovators Club
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Meru Science Innovators Club.
 * You shall not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement you entered into with Meru Science Innovators Club.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 *
 * NO WARRANTY: This software is provided "as is" without warranty of any kind,
 * either express or implied, including but not limited to the implied warranties
 * of merchantability and fitness for a particular purpose.
 */
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    id("kotlinx-serialization")
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.newton.events"
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    // Hilt
    implementation(Dependencies.hiltAndroid)
    ksp(Dependencies.hiltCompiler)

    // navigation
    implementation(Dependencies.hiltNavigation)
    implementation(Dependencies.composeNavigation)

    // Retrofit
    implementation(Dependencies.retrofit)
    implementation(Dependencies.retrofit2Converter)
    implementation(Dependencies.gsonCoverter)
    implementation(Dependencies.kotlinxSerialization)

    // Coil
    implementation(Dependencies.coilCompose)
    implementation(Dependencies.coilNetwork)

    // paging
    implementation(Dependencies.pagingCompose)
    implementation(Dependencies.pagingRuntime)

    // room
    implementation(Dependencies.roomKtx)
    ksp(Dependencies.roomCompiler)
    implementation(Dependencies.roomRuntime)
    implementation(libs.androidx.room.paging)

    // material icons
    implementation(Dependencies.material_icons_core)
    implementation(Dependencies.material_icons_extended)

    implementation(Dependencies.zxing_qr)

    implementation(Dependencies.timber)

    implementation(project(":core"))
    implementation(project(":database"))
    implementation(project(":shared-ui"))
    implementation(project(":features:auth"))
    implementation(project(":navigation"))
}
