import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.android.application)
    alias(libs.plugins.metro)
}

android {
    namespace = "${libs.versions.app.pkg.get()}.androidApp"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()

        applicationId = libs.versions.app.pkg.get()
        versionCode = libs.versions.version.code.get().toInt()
        versionName = libs.versions.version.name.get()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    androidComponents.onVariants { variant ->
        val appName = libs.versions.app.pkg.get()
        val versionName = libs.versions.version.name.get()
        val versionCode = libs.versions.version.code.get().toInt()
        variant.outputs.forEach { output ->
            if (output is com.android.build.api.variant.impl.VariantOutputImpl) {
                val apkName = "${appName}-${versionName}-${versionCode}.apk"
                output.outputFileName = apkName
            }
        }
    }
}

kotlin {
    compilerOptions { jvmTarget.set(JvmTarget.JVM_21) }
}

dependencies {
    implementation(project(":sharedUI"))
    implementation(libs.androidx.activity.compose)
    implementation(libs.metrox.viewmodel.compose)
}
