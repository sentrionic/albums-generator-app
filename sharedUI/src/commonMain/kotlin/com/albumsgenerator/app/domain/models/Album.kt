package com.albumsgenerator.app.domain.models

import androidx.compose.runtime.Immutable
import com.albumsgenerator.app.domain.core.StreamingServices
import com.albumsgenerator.app.presentation.utils.Platform
import com.albumsgenerator.app.presentation.utils.getCurrentPlatform
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Immutable
data class Album(
    val amazonMusicId: String?,
    val appleMusicId: String?,
    val artist: String,
    val artistOrigin: String?,
    val deezerId: String?,
    val genres: ImmutableList<String>,
    val globalReviewsUrl: String,
    val images: ImmutableList<String>,
    val name: String,
    val qobuzId: String?,
    val releaseDate: String,
    val slug: String,
    val spotifyId: String?,
    val subGenres: ImmutableList<String>,
    val tidalId: String?,
    val uuid: String,
    val wikipediaUrl: String,
    val youtubeMusicId: String?,
    val summary: String?,
    val type: AlbumType,
) {
    val coverUrl by lazy {
        images.firstOrNull()
    }

    val responsiveWikipediaUrl
        get() = if (getCurrentPlatform() == Platform.DESKTOP) {
            wikipediaUrl
        } else {
            "$wikipediaUrl?mobileaction=toggle_view_mobile"
        }

    val streamingServices by lazy {
        buildList {
            if (!amazonMusicId.isNullOrEmpty()) {
                add(StreamingServices.AMAZON)
            }

            if (!appleMusicId.isNullOrEmpty()) {
                add(StreamingServices.APPLE)
            }

            if (!deezerId.isNullOrEmpty()) {
                add(StreamingServices.DEEZER)
            }

            if (!qobuzId.isNullOrEmpty()) {
                add(StreamingServices.QOBUZ)
            }

            if (!spotifyId.isNullOrEmpty()) {
                add(StreamingServices.SPOTIFY)
            }

            if (!tidalId.isNullOrEmpty()) {
                add(StreamingServices.TIDAL)
            }

            if (!youtubeMusicId.isNullOrEmpty()) {
                add(StreamingServices.YOUTUBE)
            }
        }.toImmutableList()
    }

    fun serviceUrl(service: StreamingServices): String {
        val url = when (service) {
            StreamingServices.AMAZON -> amazonMusicId
            StreamingServices.APPLE -> appleMusicId
            StreamingServices.DEEZER -> deezerId
            StreamingServices.QOBUZ -> qobuzId
            StreamingServices.SPOTIFY -> spotifyId
            StreamingServices.TIDAL -> tidalId
            StreamingServices.YOUTUBE -> youtubeMusicId
        }
        return url.orEmpty()
    }
}
