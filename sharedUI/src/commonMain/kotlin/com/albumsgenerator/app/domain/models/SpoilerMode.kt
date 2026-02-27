package com.albumsgenerator.app.domain.models

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.spoiler_mode_hidden_description
import albumsgenerator.sharedui.generated.resources.spoiler_mode_hidden_label
import albumsgenerator.sharedui.generated.resources.spoiler_mode_partial_description
import albumsgenerator.sharedui.generated.resources.spoiler_mode_partial_label
import albumsgenerator.sharedui.generated.resources.spoiler_mode_visible_description
import albumsgenerator.sharedui.generated.resources.spoiler_mode_visible_label
import org.jetbrains.compose.resources.StringResource

enum class SpoilerMode(val label: StringResource, val description: StringResource) {
    VISIBLE(
        label = Res.string.spoiler_mode_visible_label,
        description = Res.string.spoiler_mode_visible_description,
    ),
    PARTIAL(
        label = Res.string.spoiler_mode_partial_label,
        description = Res.string.spoiler_mode_partial_description,
    ),
    HIDDEN(
        label = Res.string.spoiler_mode_hidden_label,
        description = Res.string.spoiler_mode_hidden_description,
    ),
    ;

    val isOn get() = this != VISIBLE
}
