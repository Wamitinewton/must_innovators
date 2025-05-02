package dependencies

import versions.Versions

object Dependencies {
    // Android Core
    const val coreKtx = "androidx.core:core-ktx:1.12.0"
    const val appCompat = "androidx.appcompat:appcompat:1.6.1"
    const val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:2.8.7"
    const val activityCompose = "androidx.activity:activity-compose:1.9.3"

    // Compose
    const val composeBom = "androidx.compose:compose-bom:2024.04.01"
    const val composeUi = "androidx.compose.ui:ui"
    const val composeUiGraphics = "androidx.compose.ui:ui-graphics"
    const val composeUiToolingPreview = "androidx.compose.ui:ui-tooling-preview"
    const val composeMaterial3 = "androidx.compose.material3:material3"
    const val composeUiTextAndroid = "androidx.compose.ui:ui-text-android"
    const val composeUiTooling = "androidx.compose.ui:ui-tooling"
    const val composeTestManifest = "androidx.compose.ui:ui-test-manifest"

    // Hilt
    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    const val hiltAgp = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
    const val hiltNavigation = "androidx.hilt:hilt-navigation-compose:${Versions.hiltNavigationCompose}"
    const val hiltWorker = "androidx.hilt:hilt-work:${Versions.hiltNavigationCompose}"

    // Navigation
    const val composeNavigation = "androidx.navigation:navigation-compose:${Versions.composeNav}"

    // Layouts
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.2.0"
    const val composeConstraint = "androidx.constraintlayout:constraintlayout-compose:1.0.1"

    // DataStore
    const val datastorePrefs = "androidx.datastore:datastore-preferences:1.0.0"
    const val cryptoDatastore = "androidx.security:security-crypto:1.1.0-alpha06"

    // Room
    const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"

    // Icons & UI Components
    const val extendedIcons = "androidx.compose.material:material-icons-extended:1.7.7"
    const val materialIconsCore = "androidx.compose.material:material-icons-core:1.2.0"
    const val materialIconsExtended = "androidx.compose.material:material-icons-extended:1.2.0"

    // Background processing
    const val work = "androidx.work:work-runtime-ktx:${Versions.workRuntimeKtx}"

    // Logging
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    // Images
    const val coilCompose = "io.coil-kt.coil3:coil-compose:3.1.0"
    const val coilNetwork = "io.coil-kt.coil3:coil-network-okhttp:3.1.0"
    const val lottieCompose = "com.airbnb.android:lottie-compose:5.0.3"

    // Paging
    const val pagingRuntime = "androidx.paging:paging-runtime-ktx:3.1.1"
    const val pagingCompose = "androidx.paging:paging-compose:1.0.0-alpha18"

    // Networking
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val okhttpLogger = "com.squareup.okhttp3:logging-interceptor:4.12.0"
    const val gsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.converterGson}"
    const val retrofit2Converter = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.retrofit2KotlinxSerializationConverter}"

    // Serialization
    const val kotlinxSerialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.kotlinXSerialization}"
    const val kotlinxDatetime = "org.jetbrains.kotlinx:kotlinx-datetime:0.6.2"

    // Accompanist
    const val systemUi = "com.google.accompanist:accompanist-systemuicontroller:0.24.10-beta"
    const val pager = "com.google.accompanist:accompanist-pager:0.24.10-beta"
    const val pagerIndicators = "com.google.accompanist:accompanist-pager-indicators:0.18.0"
    const val swipeRefresh = "com.google.accompanist:accompanist-swiperefresh:0.36.0"
    const val accompanistPermissions = "com.google.accompanist:accompanist-permissions:0.32.0"

    // Utils
    const val jaroWinkler = "info.debatty:java-string-similarity:2.0.0"
    const val zxingQr = "com.journeyapps:zxing-android-embedded:4.1.0"
    const val initializer = "androidx.startup:startup-runtime:${Versions.initilizer}"
    const val reorderable = "org.burnoutcrew.composereorderable:reorderable:${Versions.reordable}"
    const val chromeView = "androidx.browser:browser:1.8.0"

    // Testing
    const val junit = "junit:junit:${Versions.junit}"
    const val androidXJunit = "androidx.test.ext:junit:${Versions.androidXJunit}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"
}