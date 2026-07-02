package com.albumsgenerator.app.datasources.network.dtos

import com.albumsgenerator.app.domain.core.immutableMap
import com.albumsgenerator.app.domain.core.orEmpty
import com.albumsgenerator.app.domain.models.AlbumStats
import com.albumsgenerator.app.domain.models.AlbumType
import kotlinx.collections.immutable.toImmutableList
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Stats(
    @SerialName("albums")
    val albums: List<AlbumStatsDto>,
)

@Serializable
data class AlbumStatsDto(
    @SerialName("artist")
    val artist: String,
    @SerialName("artistOrigin")
    val artistOrigin: String? = null,
    @SerialName("averageRating")
    val averageRating: Double,
    @SerialName("controversialScore")
    val controversialScore: Double,
    @SerialName("genres")
    val genres: List<String>,
    @SerialName("globalReviewsUrl")
    val globalReviewsUrl: String,
    @SerialName("images")
    val images: List<ImageDto>,
    @SerialName("name")
    val name: String,
    @SerialName("releaseDate")
    val releaseDate: String,
    @SerialName("slug")
    val slug: String,
    @SerialName("spotifyId")
    val spotifyId: String? = null,
    @SerialName("styles")
    val styles: List<String>? = null,
    @SerialName("votes")
    val votes: Int,
    @SerialName("votesByGrade")
    val votesByGrade: VotesByGradeDto? = null,
) {
    fun toDomain(type: AlbumType): AlbumStats = AlbumStats(
        artist = artist,
        artistOrigin = artistOrigin ?: "other",
        averageRating = averageRating,
        controversialScore = controversialScore,
        genres = genres.toImmutableList(),
        globalReviewsUrl = globalReviewsUrl,
        images = images.immutableMap { it.url },
        name = name,
        releaseDate = releaseDate,
        slug = slug,
        spotifyId = spotifyId,
        styles = styles?.toImmutableList().orEmpty(),
        votes = votes,
        votesByGrade = votesByGrade?.toDomain(),
        type = type,
    )
}
