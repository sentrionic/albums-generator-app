package com.albumsgenerator.app.presentation.screens.current

import androidx.compose.runtime.Immutable
import com.albumsgenerator.app.domain.core.emptyImmutableList
import com.albumsgenerator.app.domain.models.History
import com.albumsgenerator.app.domain.models.Project
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class CurrentAlbumState(
    val project: Project,
    val previousAlbums: ImmutableList<History> = emptyImmutableList(),
)
