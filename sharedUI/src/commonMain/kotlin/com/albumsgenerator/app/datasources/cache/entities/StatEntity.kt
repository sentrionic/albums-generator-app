package com.albumsgenerator.app.datasources.cache.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.albumsgenerator.app.datasources.network.dtos.AlbumStatsDto
import com.albumsgenerator.app.domain.models.AlbumStats
import kotlinx.serialization.json.Json

@Entity(tableName = "stats")
data class StatEntity(
    @ColumnInfo("artist")
    val artist: String,
    @ColumnInfo("artist_origin")
    val artistOrigin: String,
    @ColumnInfo("average_rating")
    val averageRating: Double,
    @ColumnInfo("controversial_score")
    val controversialScore: Double,
    @ColumnInfo("genres")
    val genres: String,
    @ColumnInfo("global_reviews_url")
    val globalReviewsUrl: String,
    @ColumnInfo("images")
    val images: String,
    @PrimaryKey
    val name: String,
    @ColumnInfo("release_date")
    val releaseDate: String,
    @ColumnInfo("slug")
    val slug: String,
    @ColumnInfo("spotify_id")
    val spotifyId: String?,
    @ColumnInfo("styles")
    val styles: String,
    @ColumnInfo("votes")
    val votes: Int,
    @ColumnInfo("votes_by_grade")
    val votesByGrade: String,
) {
    fun toDomain(): AlbumStats = AlbumStats(
        artist = artist,
        artistOrigin = artistOrigin,
        averageRating = averageRating,
        controversialScore = controversialScore,
        genres = genres.split(SEPARATOR),
        globalReviewsUrl = globalReviewsUrl,
        images = images.split(SEPARATOR),
        name = name,
        releaseDate = releaseDate,
        slug = slug,
        spotifyId = spotifyId,
        styles = styles.split(SEPARATOR),
        votes = votes,
        votesByGrade = Json.decodeFromString<AlbumStatsDto.VotesByGradeDto>(votesByGrade)
            .toDomain(),
    )

    companion object {
        const val SEPARATOR = ","

        fun fromDomain(stat: AlbumStats): StatEntity = StatEntity(
            artist = stat.artist,
            artistOrigin = stat.artistOrigin,
            averageRating = stat.averageRating,
            controversialScore = stat.controversialScore,
            genres = stat.genres.joinToString(SEPARATOR),
            globalReviewsUrl = stat.globalReviewsUrl,
            images = stat.images.joinToString(SEPARATOR),
            name = stat.name,
            releaseDate = stat.releaseDate,
            slug = stat.slug,
            spotifyId = stat.spotifyId,
            styles = stat.styles.joinToString(SEPARATOR),
            votes = stat.votes,
            votesByGrade = Json.encodeToString(
                AlbumStatsDto.VotesByGradeDto.fromDomain(stat.votesByGrade),
            ),
        )
    }
}
