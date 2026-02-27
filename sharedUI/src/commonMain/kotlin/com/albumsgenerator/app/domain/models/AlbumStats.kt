package com.albumsgenerator.app.domain.models

import androidx.compose.runtime.Immutable

@Immutable
data class AlbumStats(
    val artist: String,
    val artistOrigin: String,
    val averageRating: Double,
    val controversialScore: Double,
    val genres: List<String>,
    val globalReviewsUrl: String,
    val images: List<String>,
    val name: String,
    val releaseDate: String,
    val slug: String,
    val spotifyId: String?,
    val styles: List<String>,
    val votes: Int,
    val votesByGrade: VotesByGrade,
) {
    val votesList by lazy {
        listOf(
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

    data class VotesByGrade(val x1: Int, val x2: Int, val x3: Int, val x4: Int, val x5: Int) {
        val totalVotes by lazy {
            x1 + x2 + x3 + x4 + x5
        }

        val average: Int by lazy {
            x1 + x2 * 2 + x3 * 3 + x4 * 4 + x5 * 5
        }
    }
}

fun List<AlbumStats>.globalAverage(): Float =
    (sumOf { it.votesByGrade.average }.toFloat() / sumOf { it.summedVotes }) + 0.1f
