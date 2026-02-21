package com.albumsgenerator.app.presentation.screens.album

import androidx.compose.runtime.Immutable
import com.albumsgenerator.app.domain.core.StreamingServices
import com.albumsgenerator.app.domain.models.AlbumStats
import com.albumsgenerator.app.domain.models.History

@Immutable
data class AlbumState(val history: History?, val stats: AlbumStats) {
    val coverUrl get() = history?.album?.coverUrl ?: stats.images.firstOrNull()
    val name get() = history?.album?.name ?: stats.name
    val artist get() = history?.album?.artist ?: stats.artist
    val releaseDate get() = history?.album?.releaseDate ?: stats.releaseDate
    val streamingServices
        get() = history?.album?.streamingServices ?: listOf(StreamingServices.SPOTIFY)
    val genres = history?.album?.genres ?: stats.genres
    val subGenres = history?.album?.subGenres ?: stats.styles

    fun serviceUrl(service: StreamingServices): String =
        history?.album?.serviceUrl(service) ?: stats.spotifyId.orEmpty()
}
