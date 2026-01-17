package com.albumsgenerator.app.datasources.network.dtos

import com.albumsgenerator.app.domain.models.Album
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AlbumDto(
    @SerialName("amazonMusicId")
    val amazonMusicId: String? = null,
    @SerialName("appleMusicId")
    val appleMusicId: String? = null,
    @SerialName("artist")
    val artist: String,
    @SerialName("artistOrigin")
    val artistOrigin: String? = null,
    @SerialName("deezerId")
    val deezerId: String? = null,
    @SerialName("genres")
    val genres: List<String>,
    @SerialName("globalReviewsUrl")
    val globalReviewsUrl: String,
    @SerialName("images")
    val images: List<ImageDto>,
    @SerialName("name")
    val name: String,
    @SerialName("qobuzId")
    val qobuzId: String? = null,
    @SerialName("releaseDate")
    val releaseDate: String,
    @SerialName("slug")
    val slug: String,
    @SerialName("spotifyId")
    val spotifyId: String? = null,
    @SerialName("subGenres")
    val subGenres: List<String>,
    @SerialName("tidalId")
    val tidalId: String? = null,
    @SerialName("uuid")
    val uuid: String,
    @SerialName("wikipediaUrl")
    val wikipediaUrl: String,
    @SerialName("youtubeMusicId")
    val youtubeMusicId: String? = null,
) {
    fun toDomain(): Album = Album(
        amazonMusicId = amazonMusicId,
        appleMusicId = appleMusicId,
        artist = artist,
        artistOrigin = artistOrigin,
        deezerId = deezerId,
        genres = genres,
        globalReviewsUrl = globalReviewsUrl,
        images = images.map { it.url },
        name = name,
        qobuzId = qobuzId,
        releaseDate = releaseDate,
        slug = slug,
        spotifyId = spotifyId,
        subGenres = subGenres,
        tidalId = tidalId,
        uuid = uuid,
        wikipediaUrl = wikipediaUrl,
        youtubeMusicId = youtubeMusicId,
        summary = null,
        type = Album.AlbumType.OFFICIAL,
    )
}
