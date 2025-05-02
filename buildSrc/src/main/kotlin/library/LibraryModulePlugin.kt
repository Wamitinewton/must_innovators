package library

import com.android.build.gradle.LibraryExtension
import config.AppConfig
import extensions.addComposeDependencies
import extensions.addCoreAndroidDependencies
import extensions.addHiltDependencies
import extensions.addTestDependencies
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import plugins.Plugins

class LibraryModulePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            applyPlugins()
            configureAndroid()
            configureDependencies()
        }
    }

    private fun Project.applyPlugins() {
        plugins.apply(Plugins.androidLibrary)
        plugins.apply(Plugins.kotlinAndroid)
        plugins.apply(Plugins.kotlinParcelize)
        plugins.apply(Plugins.kotlinxSerialization)
        plugins.apply(Plugins.ksp)
        plugins.apply(Plugins.hiltAndroid)
        plugins.apply(Plugins.compose)
    }

    private fun Project.configureAndroid() {
        extensions.configure<LibraryExtension> {
            compileSdk = AppConfig.compileSdk

            defaultConfig {
                minSdk = AppConfig.minSdk
                testInstrumentationRunner = AppConfig.testInstrumentationRunner
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
                sourceCompatibility = JavaVersion.VERSION_21
                targetCompatibility = JavaVersion.VERSION_21
            }

            buildFeatures {
                compose = true
            }

            composeOptions {
                kotlinCompilerExtensionVersion = "2.0.0"
            }
        }
    }

    private fun Project.configureDependencies() {
        dependencies {
            addCoreAndroidDependencies()
            addComposeDependencies()
            addTestDependencies()
            addHiltDependencies()
        }
    }
}