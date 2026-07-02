package com.albumsgenerator.app.datasources.cache.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.albumsgenerator.app.datasources.network.dtos.VotesByGradeDto
import com.albumsgenerator.app.domain.core.Utils
import com.albumsgenerator.app.domain.core.emptyImmutableList
import com.albumsgenerator.app.domain.core.immutableSplit
import com.albumsgenerator.app.domain.models.AlbumStats
import com.albumsgenerator.app.domain.models.AlbumType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.serialization.json.Json

@Entity(tableName = "stats")
data class StatEntity(
    @PrimaryKey
    val id: String,
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
    @ColumnInfo
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
    val votesByGrade: String?,
    @ColumnInfo("album_type")
    val type: Int,
) {
    fun toDomain(): AlbumStats = AlbumStats(
        artist = artist,
        artistOrigin = artistOrigin,
        averageRating = averageRating,
        controversialScore = controversialScore,
        genres = genres.splitOrEmpty(),
        globalReviewsUrl = globalReviewsUrl,
        images = images.splitOrEmpty(),
        name = name,
        releaseDate = releaseDate,
        slug = slug,
        spotifyId = spotifyId,
        styles = styles.splitOrEmpty(),
        votes = votes,
        votesByGrade = votesByGrade?.let {
            Json.decodeFromString<VotesByGradeDto>(it)
                .toDomain()
        },
        type = AlbumType.entries.first { it.ordinal == type },
    )

    companion object {
        const val SEPARATOR = ","

        private fun String.splitOrEmpty(): ImmutableList<String> = if (isEmpty()) {
            emptyImmutableList()
        } else {
            immutableSplit(SEPARATOR)
        }

        fun fromDomain(stat: AlbumStats): StatEntity = StatEntity(
            id = "${Utils.slugify(stat.artist)}-${Utils.slugify(stat.name)}",
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
            votesByGrade = stat.votesByGrade?.let {
                Json.encodeToString(
                    VotesByGradeDto.fromDomain(it),
                )
            },
            type = stat.type.ordinal,
        )
    }
}
