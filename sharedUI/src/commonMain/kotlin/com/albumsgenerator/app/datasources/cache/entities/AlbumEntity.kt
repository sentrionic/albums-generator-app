package com.albumsgenerator.app.datasources.cache.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.albumsgenerator.app.domain.models.Album

@Entity(tableName = "albums")
data class AlbumEntity(
    @ColumnInfo("amazon_music_id")
    val amazonMusicId: String? = null,
    @ColumnInfo("apple_music_id")
    val appleMusicId: String? = null,
    @ColumnInfo(name = "artist", index = true)
    val artist: String,
    @ColumnInfo("artist_origin")
    val artistOrigin: String? = null,
    @ColumnInfo("deezer_id")
    val deezerId: String? = null,
    @ColumnInfo("genres")
    val genres: String,
    @ColumnInfo("global_reviews_url")
    val globalReviewsUrl: String,
    @ColumnInfo("images")
    val images: String,
    @ColumnInfo(name = "name", index = true)
    val name: String,
    @ColumnInfo("qobuz_id")
    val qobuzId: String? = null,
    @ColumnInfo("release_date")
    val releaseDate: String,
    @ColumnInfo("slug")
    val slug: String,
    @ColumnInfo("spotify_id")
    val spotifyId: String? = null,
    @ColumnInfo("sub_genres")
    val subGenres: String,
    @ColumnInfo("tidal_id")
    val tidalId: String? = null,
    @PrimaryKey
    val uuid: String,
    @ColumnInfo("wikipedia_url")
    val wikipediaUrl: String,
    @ColumnInfo("youtube_music_id")
    val youtubeMusicId: String? = null,
    @ColumnInfo("summary")
    val summary: String? = null,
    @ColumnInfo("type")
    val type: Int,
) {
    fun toDomain(): Album = Album(
        amazonMusicId = amazonMusicId,
        appleMusicId = appleMusicId,
        artist = artist,
        artistOrigin = artistOrigin,
        deezerId = deezerId,
        genres = genres.split(SEPARATOR),
        globalReviewsUrl = globalReviewsUrl,
        images = images.split(SEPARATOR),
        name = name,
        qobuzId = qobuzId,
        releaseDate = releaseDate,
        slug = slug,
        spotifyId = spotifyId,
        subGenres = subGenres.split(SEPARATOR),
        tidalId = tidalId,
        uuid = uuid,
        wikipediaUrl = wikipediaUrl,
        youtubeMusicId = youtubeMusicId,
        summary = summary,
        type = Album.AlbumType.entries.getOrNull(type) ?: Album.AlbumType.OFFICIAL,
    )

    companion object {
        const val SEPARATOR = ","

        fun fromDomain(album: Album): AlbumEntity = AlbumEntity(
            amazonMusicId = album.amazonMusicId,
            appleMusicId = album.appleMusicId,
            artist = album.artist,
            artistOrigin = album.artistOrigin,
            deezerId = album.deezerId,
            genres = album.genres.joinToString(SEPARATOR),
            globalReviewsUrl = album.globalReviewsUrl,
            images = album.images.joinToString(SEPARATOR),
            name = album.name,
            qobuzId = album.qobuzId,
            releaseDate = album.releaseDate,
            slug = album.slug,
            spotifyId = album.spotifyId,
            subGenres = album.subGenres.joinToString(SEPARATOR),
            tidalId = album.tidalId,
            uuid = album.uuid,
            wikipediaUrl = album.wikipediaUrl,
            youtubeMusicId = album.youtubeMusicId,
            summary = album.summary,
            type = album.type.ordinal,
        )
    }
}
