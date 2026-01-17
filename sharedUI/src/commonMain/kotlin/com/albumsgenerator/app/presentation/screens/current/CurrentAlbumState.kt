package com.albumsgenerator.app.presentation.screens.current

import androidx.compose.runtime.Immutable
import com.albumsgenerator.app.domain.models.History
import com.albumsgenerator.app.domain.models.Project

@Immutable
data class CurrentAlbumState(val project: Project, val previousAlbums: List<History> = emptyList())
