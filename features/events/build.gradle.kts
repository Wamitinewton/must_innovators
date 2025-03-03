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
    //Hilt
    implementation(Dependencies.hiltAndroid)
    ksp(Dependencies.hiltCompiler)

    // navigation
    implementation(Dependencies.hiltNavigation)
    implementation(Dependencies.composeNavigation)

    //Retrofit
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

            //room
    implementation(Dependencies.roomKtx)
    ksp(Dependencies.roomCompiler)
    implementation(Dependencies.roomRuntime)
    implementation("androidx.room:room-paging:2.6.1")

    //material icons
    implementation(Dependencies.material_icons_core)
    implementation(Dependencies.material_icons_extended)

    //zxing
    implementation(Dependencies.zxing_qr)





    implementation(project(":core"))
    implementation(project(":database"))
    implementation(project(":common_ui"))
    implementation(project(":features:auth"))
}