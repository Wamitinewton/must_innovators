import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    alias(libs.plugins.kotlin.compose)
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    id("kotlinx-serialization")
    id("com.google.devtools.ksp")
    id("com.google.gms.google-services")
}

val properties = Properties()
val store = rootProject.file("keys.properties")

// Early loading of properties
if (store.exists()) {
    properties.load(store.inputStream())
} else {
    throw GradleException("keys.properties file not found")
}


val prodBackendUrl = properties.getProperty("PROD_BACKEND_URL")
    ?: throw GradleException("PROD_BACKEND_URL not found in keys.properties")
val devBackendUrl = properties.getProperty("DEV_BACKEND_URL")
    ?: throw GradleException("DEV_BACKEND_URL not found in keys.properties")
val stagingBackendUrl = properties.getProperty("STAGING_BACKEND_URL")
    ?: throw GradleException("STAGING_BACKEND_URL not found in keys.properties")


android {
    namespace = "com.newton.meruinnovators"
    compileSdk = 35


    defaultConfig {
        multiDexEnabled = true
        try {
            val keystoreFile = rootProject.file("keys.properties")
            if (keystoreFile.exists()) {
                properties.load(keystoreFile.inputStream())
            } else {
                throw GradleException("keys.properties file not found")
            }
        } catch (e: Exception) {
            logger.warn("Warning: ${e.message}")
        }

//        val backendUrl = properties.getProperty("BACKEND_URL")
//            ?: throw GradleException("BACKEND_URL not found in keys.properties")

        applicationId = "com.newton.meruinnovators"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }

    signingConfigs {

        create("release") {
            val keystoreFile = properties.getProperty("RELEASE_STORE_FILE")
                ?: throw GradleException("store file not found in keys.properties")
            val keystorePassword = properties.getProperty("RELEASE_STORE_PASSWORD")
                ?: throw GradleException("store password not found in keys.properties")
            val keyalias = properties.getProperty("RELEASE_KEY_ALIAS")
                ?: throw GradleException("key alias not found in keys.properties")
            val keyaliasPassword = properties.getProperty("RELEASE_KEY_PASSWORD")
                ?: throw GradleException("alias pwd not found in keys.properties")

            storeFile = file(keystoreFile)
            storePassword = keystorePassword
            keyAlias = keyalias
            keyPassword = keyaliasPassword
        }
    }
    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            isShrinkResources = false
            buildConfigField("String", "BACKEND_URL", "\"$devBackendUrl\"")
        }
        create("stagging") {
            isMinifyEnabled = true
            isShrinkResources = false
            buildConfigField("String", "BACKEND_URL", "\"$stagingBackendUrl\"")
        }
        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BACKEND_URL", "\"$prodBackendUrl\"")
        }
    }
    flavorDimensions += "appStatus"
    productFlavors {
        create("production") {
            applicationIdSuffix = ""
            dimension = "appStatus"
            manifestPlaceholders["appName"] = "M.U.S.I.C"
        }
        create("dev") {
            applicationIdSuffix = ""
            dimension = "appStatus"
            manifestPlaceholders["appName"] = "[DEV] M.U.S.I.C"
        }
        create("staging") {
            applicationIdSuffix = ".stg"
            dimension = "appStatus"
            manifestPlaceholders["appName"] = "[STG] M.U.S.I.C"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
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
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //hilt
    implementation(Dependencies.hiltAndroid)
    ksp(Dependencies.hiltCompiler)
    implementation(Dependencies.hiltNavigation)

    //Retrofit
    implementation(Dependencies.retrofit)
    implementation(Dependencies.retrofit2Converter)
    implementation(Dependencies.gsonCoverter)
    implementation(Dependencies.kotlinxSerialization)
    implementation(Dependencies.okhttp_logger)

    //worker
    implementation(Dependencies.work)
    implementation(Dependencies.hiltWorker)

    //Timber
    implementation(Dependencies.timber)

    //coil
    implementation(Dependencies.coilCompose)
    implementation(Dependencies.coilNetwork)

    // system ui
    implementation(Dependencies.systemUi)

    implementation(Dependencies.extendedIcons)

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.10.0"))
    implementation("com.google.firebase:firebase-messaging")
    implementation("com.google.firebase:firebase-analytics")


    implementation(project(":features:auth"))
    implementation(project(":core"))
    implementation(project(":common_ui"))
    implementation(project(":features:events"))
    implementation(project(":features:blogs"))
    implementation(project(":features:account"))
    implementation(project(":features:home"))
    implementation(project(":features:admin"))
    implementation(project(":database"))
    implementation(project(":features:about_us"))
    implementation(project(":notifications"))
    implementation(project(":features:feedback"))
}


