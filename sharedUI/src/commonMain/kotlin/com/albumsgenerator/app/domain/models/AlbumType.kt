package com.albumsgenerator.app.domain.models

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.album_type_official
import albumsgenerator.sharedui.generated.resources.album_type_user
import org.jetbrains.compose.resources.StringResource

enum class AlbumType(val label: StringResource) {
    OFFICIAL(label = Res.string.album_type_official),
    USER(label = Res.string.album_type_user),
}
