package com.albumsgenerator.app.presentation.screens.artist

import androidx.compose.runtime.Immutable
import com.albumsgenerator.app.domain.models.Album
import com.albumsgenerator.app.domain.models.AlbumStats

@Immutable
data class ArtistState(
    val albums: List<Album>,
    val albumStats: List<AlbumStats>,
    val unknownAlbums: Int,
)
