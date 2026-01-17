package com.albumsgenerator.app.presentation.utils

enum class Platform {
    ANDROID,
    DESKTOP,
    IOS,
}

expect fun getCurrentPlatform(): Platform
