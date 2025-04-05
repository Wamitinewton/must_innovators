plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("kotlinx-serialization")
}

android {
    namespace = "com.newton.core"
    compileSdk = 35

    defaultConfig {
        minSdk = 28

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(Dependencies.hiltNavigation)
    implementation(Dependencies.composeNavigation)

    // Retrofit
    implementation(Dependencies.retrofit)
    implementation(Dependencies.retrofit2Converter)
    implementation(Dependencies.gsonCoverter)
    implementation(Dependencies.kotlinxSerialization)

    implementation(Dependencies.work)
    implementation(Dependencies.hiltWorker)

    // paging
    implementation(Dependencies.pagingCompose)
    implementation(Dependencies.pagingRuntime)

    // timber
    implementation(Dependencies.timber)
}
