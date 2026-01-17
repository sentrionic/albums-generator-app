import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.android.kmp.library)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.metro)
    alias(libs.plugins.room)
    alias(libs.plugins.ksp)
    alias(libs.plugins.gradle.buildconfig.plugin)
    alias(libs.plugins.kotlinter)
}

kotlin {
    android {
        namespace = "com.albumsgenerator.app"
        compileSdk = 36
        minSdk = 28
        androidResources.enable = true
        compilerOptions { jvmTarget.set(JvmTarget.JVM_21) }
    }

    jvm()

    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            api(libs.compose.foundation)
            api(libs.compose.material3)
            api(libs.compose.resources)
            api(libs.compose.runtime)
            api(libs.compose.ui)
            api(libs.compose.ui.tooling.preview)
            api(libs.compose.webview.multiplatform)

            implementation(libs.androidx.datastore)
            implementation(libs.androidx.datastore.preferences)
            implementation(libs.androidx.sqlite.bundled)
            implementation(libs.coil.compose)
            implementation(libs.coil.network)
            implementation(libs.compose.nav3)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.serialization)
            implementation(libs.ktor.serialization.json)
            implementation(libs.jetbrains.lifecycle.runtime.compose)
            implementation(libs.jetbrains.lifecycle.viewmodel.compose)
            implementation(libs.jetbrains.lifecycle.viewmodel.navigation3)
            implementation(libs.jetbrains.material.icons.extended)
            implementation(libs.material3.placeholder)
            implementation(libs.metrox.viewmodel.compose)
            implementation(libs.room.runtime)
            implementation(libs.touchlab.kermit)
        }

        androidMain.dependencies {
            implementation(libs.kotlinx.coroutines.android)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.metrox.viewmodel.compose)
        }

        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.metrox.viewmodel.compose)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }

    targets
        .withType<KotlinNativeTarget>()
        .matching { it.konanTarget.family.isAppleFamily }
        .configureEach {
            binaries {
                framework {
                    baseName = "SharedUI"
                    isStatic = true
                }
            }
        }

    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }
}

dependencies {
    androidRuntimeClasspath(libs.compose.ui.tooling)
}

buildConfig {
    // BuildConfig configuration here.
    // https://github.com/gmazzo/gradle-buildconfig-plugin#usage-in-kts
    packageName = "com.albumsgenerator.app"

    buildConfigField("String", "APP_REPOSITORY", "\"https://github.com/sentrionic/albums-generator-app\"")
    buildConfigField("String", "WEBSITE_API", "\"https://1001albumsgenerator.com/api/v1\"")
    buildConfigField("String", "WEBSITE_URL", "\"https://1001albumsgenerator.com\"")
    buildConfigField("int", "TOTAL_ALBUMS_COUNT", "1089")
}

room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    with(libs.room.compiler) {
        add("kspAndroid", this)
        add("kspJvm", this)
        add("kspIosArm64", this)
        add("kspIosSimulatorArm64", this)
    }
}
