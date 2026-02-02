import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.google.devtools.ksp)
    alias(libs.plugins.google.dagger.hilt.android)
}

android {
    namespace = "com.sergokuzneczow.domain"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

kotlin {
    explicitApi = ExplicitApiMode.Strict
}

dependencies {
    implementation(project(":core:network"))
    implementation(project(":core:database"))
    implementation(project(":core:model"))
    //Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    //Truth
    testImplementation(libs.truth)
    //Junit4
    testImplementation(libs.junit)
}