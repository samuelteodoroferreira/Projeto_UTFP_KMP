import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    kotlin("plugin.serialization") version libs.versions.kotlin.get()
}

kotlin {
    androidTarget()
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
            export("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
            export("io.ktor:ktor-client-core:2.3.7")
            export("io.ktor:ktor-client-content-negotiation:2.3.7")
            export("io.ktor:ktor-serialization-kotlinx-json:2.3.7")
            export("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
        }
    }
    
    sourceSets {
        commonMain.dependencies {
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
            implementation("io.ktor:ktor-client-core:2.3.7")
            implementation("io.ktor:ktor-client-content-negotiation:2.3.7")
            implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.7")
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
        }

        androidMain.dependencies {
            implementation("io.ktor:ktor-client-android:2.3.7")
        }

        iosMain.dependencies {
            implementation("io.ktor:ktor-client-darwin:2.3.7")
        }
    }
}

android {
    namespace = "utfpr.projetokmp_samuelteodoro.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "11"
    }
}
