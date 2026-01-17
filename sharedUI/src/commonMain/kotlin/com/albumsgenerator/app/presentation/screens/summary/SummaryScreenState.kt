package com.albumsgenerator.app.presentation.screens.summary

import androidx.compose.runtime.Immutable
import com.albumsgenerator.app.domain.models.Album

@Immutable
data class SummaryScreenState(
    val albumsRated: Int = 0,
    val averageRating: Float = 0.0f,
    val percentageComplete: Float = 0.0f,
    val fiveStarAlbums: List<Album> = emptyList(),
    val oneStarAlbums: List<Album> = emptyList(),
)
