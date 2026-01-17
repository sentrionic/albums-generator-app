package com.albumsgenerator.app.domain.models

import androidx.compose.runtime.Immutable

@Immutable
data class Project(
    val currentAlbum: Album,
    val currentAlbumNotes: String,
    val history: List<History>,
    val name: String,
    val paused: Boolean,
    val shareableUrl: String,
    val updateFrequency: String,
)
