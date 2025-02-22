import org.gradle.api.artifacts.dsl.DependencyHandler

object Dependencies {


    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    const val hiltAgp = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
    const val hiltNavigation = "androidx.hilt:hilt-navigation-compose:${Versions.hiltNavigationCompose}"
    const val composeNavigation = "androidx.navigation:navigation-compose:${Versions.composeNav}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.2.0"
    const val composeConstraint = "androidx.constraintlayout:constraintlayout-compose:1.0.1"
    const val datastorePrefs = "androidx.datastore:datastore-preferences"
    const val crypto_datastore = "androidx.security:security-crypto:1.1.0-alpha06"
    const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
    const val extendedIcons = "androidx.compose.material:material-icons-extended:1.7.7"
    const val work = "androidx.work:work-runtime-ktx:${Versions.workRuntimeKtx}"
    const val hiltWorker = "androidx.hilt:hilt-work:${Versions.hiltNavigationCompose}"
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    const val coilCompose = "io.coil-kt.coil3:coil-compose:3.1.0"
    const val coilNetwork = "io.coil-kt.coil3:coil-network-okhttp:3.1.0"
    const val lottieCompose = "com.airbnb.android:lottie-compose:5.0.3"
    const val pagingRuntime = "androidx.paging:paging-runtime-ktx:3.1.1"
    const val pagingCompose = "androidx.paging:paging-compose:1.0.0-alpha18"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val okhttp_logger = "com.squareup.okhttp3:logging-interceptor:4.12.0"
    const val gsonCoverter = "com.squareup.retrofit2:converter-gson:${Versions.converterGson}"
    const val retrofit2Converter = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.retrofit2KotlinxSerializationConverter}"
    const val kotlinxSerialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.kotlinXSerialization}"
    const val systemUi = "com.google.accompanist:accompanist-systemuicontroller:0.24.10-beta"
    const val pager = "com.google.accompanist:accompanist-pager:0.24.10-beta"
    const val pagerIndicators = "com.google.accompanist:accompanist-pager-indicators:0.18.0"
    const val swipeRefresh = "com.google.accompanist:accompanist-swiperefresh:0.36.0"
    const val accompanistPermissions = "com.google.accompanist:accompanist-permissions:0.32.0"
    const val jaro_winkler = "info.debatty:java-string-similarity:2.0.0"
    const val material_icons_core = "androidx.compose.material:material-icons-core:1.2.0"
    const val material_icons_extended = "androidx.compose.material:material-icons-extended:1.2.0"
    const val zxing_qr = "com.journeyapps:zxing-android-embedded:4.1.0"
}





