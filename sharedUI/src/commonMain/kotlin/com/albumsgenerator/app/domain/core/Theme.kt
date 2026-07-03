package com.albumsgenerator.app.domain.core

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.ic_auto_mode
import albumsgenerator.sharedui.generated.resources.ic_dark_mode
import albumsgenerator.sharedui.generated.resources.ic_light_mode
import albumsgenerator.sharedui.generated.resources.theme_dark
import albumsgenerator.sharedui.generated.resources.theme_light
import albumsgenerator.sharedui.generated.resources.theme_system
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

enum class Theme(val label: StringResource, val icon: DrawableResource) {
    LIGHT(
        label = Res.string.theme_light,
        icon = Res.drawable.ic_light_mode,
    ),
    DARK(
        label = Res.string.theme_dark,
        icon = Res.drawable.ic_dark_mode,
    ),
    SYSTEM(
        label = Res.string.theme_system,
        icon = Res.drawable.ic_auto_mode,
    ),
}
