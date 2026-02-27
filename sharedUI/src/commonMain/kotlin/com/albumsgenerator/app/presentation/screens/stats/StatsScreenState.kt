package com.albumsgenerator.app.presentation.screens.stats

import androidx.compose.runtime.Immutable
import com.albumsgenerator.app.domain.models.AlbumStats
import com.albumsgenerator.app.domain.models.SpoilerMode

@Immutable
data class StatsScreenState(
    val topAlbums: List<AlbumStats> = emptyList(),
    val bottomAlbums: List<AlbumStats> = emptyList(),
    val mostControversial: List<AlbumStats> = emptyList(),
    val leastControversial: List<AlbumStats> = emptyList(),
    val votes: Int = 0,
    val averageRating: Float = 0.0f,
    val spoilerMode: SpoilerMode = SpoilerMode.VISIBLE,
    val previousAlbumNames: List<String> = emptyList(),
)
