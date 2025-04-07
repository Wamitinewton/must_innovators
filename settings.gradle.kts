pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

include(
    ":app",
    ":commonUi",
    ":core",
    ":database",
    ":features:events",
    ":features:auth",
    ":features:home",
    ":features:account",
    ":features:blogs",
    ":features:admin",
    ":features:aboutUs",
    ":notifications",
    ":features:feedback",
    ":navigation",
    ":sharedPrefs",
    ":network"
)
include(":shared")
include(":features:testimonials")
include(":features:partners")
include(":features:settings")
