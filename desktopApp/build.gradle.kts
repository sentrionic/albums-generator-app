import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.metro)
}

dependencies {
    implementation(project(":sharedUI"))

    implementation(libs.metrox.viewmodel.compose)
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = libs.versions.app.name.get()
            packageVersion = libs.versions.version.name.get()
            description = libs.versions.app.description.get()
            modules("jdk.unsupported")
            modules("jdk.unsupported.desktop")

            linux {
                iconFile.set(project.file("appIcons/LinuxIcon.png"))
                menuGroup = libs.versions.app.menugroup.get()
                shortcut = true
            }
            windows {
                iconFile.set(project.file("appIcons/WindowsIcon.ico"))
                msiPackageVersion = libs.versions.version.name.get()
                shortcut = true
                dirChooser = true
                menu = true
                menuGroup = libs.versions.app.menugroup.get()
            }
            macOS {
                iconFile.set(project.file("appIcons/MacosIcon.icns"))
                bundleID = "com.albumsgenerator.app.desktopApp"
            }
        }

        buildTypes.release.proguard {
            version.set("7.6.0")
            obfuscate.set(false)
            isEnabled = false
        }
    }
}
