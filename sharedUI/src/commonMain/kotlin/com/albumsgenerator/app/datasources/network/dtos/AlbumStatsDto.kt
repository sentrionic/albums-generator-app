package com.albumsgenerator.app.datasources.network.dtos

import com.albumsgenerator.app.domain.models.AlbumStats
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
    val votesByGrade: VotesByGradeDto,
) {
    @Serializable
    data class VotesByGradeDto(
        @SerialName("1")
        val x1: Int,
        @SerialName("2")
        val x2: Int,
        @SerialName("3")
        val x3: Int,
        @SerialName("4")
        val x4: Int,
        @SerialName("5")
        val x5: Int,
    ) {
        fun toDomain(): AlbumStats.VotesByGrade = AlbumStats.VotesByGrade(
            x1 = x1,
            x2 = x2,
            x3 = x3,
            x4 = x4,
            x5 = x5,
        )

        companion object {
            fun fromDomain(votes: AlbumStats.VotesByGrade): VotesByGradeDto = VotesByGradeDto(
                x1 = votes.x1,
                x2 = votes.x2,
                x3 = votes.x3,
                x4 = votes.x4,
                x5 = votes.x5,
            )
        }
    }

    fun toDomain(): AlbumStats = AlbumStats(
        artist = artist,
        artistOrigin = artistOrigin ?: "other",
        averageRating = averageRating,
        controversialScore = controversialScore,
        genres = genres,
        globalReviewsUrl = globalReviewsUrl,
        images = images.map { it.url },
        name = name,
        releaseDate = releaseDate,
        slug = slug,
        spotifyId = spotifyId,
        styles = styles.orEmpty(),
        votes = votes,
        votesByGrade = votesByGrade.toDomain(),
    )
}
