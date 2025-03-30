import org.jetbrains.compose.ExperimentalComposeLibrary

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
}

kotlin {
    androidTarget()

    sourceSets {
        @OptIn(ExperimentalComposeLibrary::class)
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation("androidx.compose.material3:material3:1.2.0")
            implementation("androidx.compose.material3:material3-window-size-class:1.2.0")
        }
        @OptIn(ExperimentalComposeLibrary::class)
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.preview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(projects.shared)

            // Ktor
            implementation("io.ktor:ktor-client-core:2.3.7")
            implementation("io.ktor:ktor-client-content-negotiation:2.3.7")
            implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.7")
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
        }
    }
}

android {
    namespace = "utfpr.projetokmp_samuelteodoro"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "utfpr.projetokmp_samuelteodoro"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlin {
        jvmToolchain(11)
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}