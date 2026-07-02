package com.albumsgenerator.app.presentation.screens.stats

import androidx.compose.runtime.Immutable
import com.albumsgenerator.app.domain.core.Constants
import com.albumsgenerator.app.domain.core.emptyImmutableList
import com.albumsgenerator.app.domain.models.AlbumStats
import com.albumsgenerator.app.domain.models.AlbumType
import com.albumsgenerator.app.domain.models.SpoilerMode
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class StatsScreenState(
    val totalAlbums: Int = Constants.TOTAL_ALBUMS_COUNT,
    val topAlbums: ImmutableList<AlbumStats> = emptyImmutableList(),
    val bottomAlbums: ImmutableList<AlbumStats> = emptyImmutableList(),
    val mostControversial: ImmutableList<AlbumStats> = emptyImmutableList(),
    val leastControversial: ImmutableList<AlbumStats> = emptyImmutableList(),
    val votes: Int = 0,
    val averageRating: Float = 0.0f,
    val spoilerMode: SpoilerMode = SpoilerMode.VISIBLE,
    val previousAlbumNames: ImmutableList<String> = emptyImmutableList(),
    val displayType: AlbumType,
)
