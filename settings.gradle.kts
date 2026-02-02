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

rootProject.name = "ltechtest"
include(":app")
include(":core:network")
include(":feature:authorization")
include(":core:model")
include(":core:navigator")
include(":core:ui")
include(":core:domain")
include(":core:database")
include(":feature:home")
include(":feature:details")
