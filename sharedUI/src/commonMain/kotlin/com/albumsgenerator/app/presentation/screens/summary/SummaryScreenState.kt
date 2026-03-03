package com.albumsgenerator.app.presentation.screens.summary

import androidx.compose.runtime.Immutable
import com.albumsgenerator.app.domain.core.emptyImmutableList
import com.albumsgenerator.app.domain.models.Album
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class SummaryScreenState(
    val albumsRated: Int = 0,
    val averageRating: Float = 0.0f,
    val percentageComplete: Float = 0.0f,
    val fiveStarAlbums: ImmutableList<Album> = emptyImmutableList(),
    val oneStarAlbums: ImmutableList<Album> = emptyImmutableList(),
)
