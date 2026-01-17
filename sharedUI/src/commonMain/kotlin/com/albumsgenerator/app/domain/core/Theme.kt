package com.albumsgenerator.app.domain.core

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.theme_dark
import albumsgenerator.sharedui.generated.resources.theme_light
import albumsgenerator.sharedui.generated.resources.theme_system
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoMode
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.StringResource

enum class Theme(val label: StringResource, val icon: ImageVector) {
    LIGHT(
        label = Res.string.theme_light,
        icon = Icons.Filled.LightMode,
    ),
    DARK(
        label = Res.string.theme_dark,
        icon = Icons.Filled.DarkMode,
    ),
    SYSTEM(
        label = Res.string.theme_system,
        icon = Icons.Filled.AutoMode,
    ),
}
