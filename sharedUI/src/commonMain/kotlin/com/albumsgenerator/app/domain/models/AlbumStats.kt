package com.albumsgenerator.app.domain.models

import androidx.compose.runtime.Immutable
import com.albumsgenerator.app.domain.core.immutableListOf
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class AlbumStats(
    val artist: String,
    val artistOrigin: String,
    val averageRating: Double,
    val controversialScore: Double,
    val genres: ImmutableList<String>,
    val globalReviewsUrl: String,
    val images: ImmutableList<String>,
    val name: String,
    val releaseDate: String,
    val slug: String,
    val spotifyId: String?,
    val styles: ImmutableList<String>,
    val votes: Int,
    val votesByGrade: VotesByGrade,
) {
    val votesList by lazy {
        immutableListOf(
            votesByGrade.x1,
            votesByGrade.x2,
            votesByGrade.x3,
            votesByGrade.x4,
            votesByGrade.x5,
        )
    }

    val summedVotes by lazy {
        votesByGrade.totalVotes
    }

    val maxValue by lazy {
        votesList.maxOf { it / summedVotes.toFloat() } + 0.2f
    }
}

fun List<AlbumStats>.globalAverage(): Float =
    (sumOf { it.votesByGrade.average }.toFloat() / sumOf { it.summedVotes }) + 0.1f
