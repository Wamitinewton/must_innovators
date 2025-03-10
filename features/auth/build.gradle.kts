plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android") // Add this line
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    id("kotlinx-serialization")
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.newton.auth"
    compileSdk = 35

    defaultConfig {
        minSdk = 28

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        vectorDrawables {
            useSupportLibrary = true
        }
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
    buildFeatures {
        compose = true
    }
//    composeOptions {
//        kotlinCompilerExtensionVersion = "1.5.9"
//    }

    kotlinOptions {
        jvmTarget = "11"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
//    implementation(libs.material)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(project(":common_ui"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    //Hilt
    implementation(Dependencies.hiltAndroid)
    ksp(Dependencies.hiltCompiler)


    implementation(Dependencies.hiltNavigation)
    implementation(Dependencies.composeNavigation)

    //Retrofit
    implementation(Dependencies.retrofit)
    implementation(Dependencies.retrofit2Converter)
    implementation(Dependencies.gsonCoverter)
    implementation(Dependencies.kotlinxSerialization)

    //Workmanager
    implementation(Dependencies.work)
    implementation(Dependencies.hiltWorker)

    //coil
    implementation(Dependencies.coilCompose)
    implementation(Dependencies.coilNetwork)

    implementation(Dependencies.crypto_datastore)

    implementation(Dependencies.extendedIcons)

    implementation(Dependencies.timber)

    implementation(project(":core"))
    implementation(project(":database"))
    implementation(project(":common_ui"))


}