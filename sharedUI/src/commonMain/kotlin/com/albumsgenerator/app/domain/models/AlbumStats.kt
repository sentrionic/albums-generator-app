package com.albumsgenerator.app.domain.models

import androidx.compose.runtime.Immutable
import com.albumsgenerator.app.domain.core.emptyImmutableList
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
    val votesByGrade: VotesByGrade?,
    val type: AlbumType,
) {
    val votesList by lazy {
        if (votesByGrade != null) {
            immutableListOf(
                votesByGrade.x1,
                votesByGrade.x2,
                votesByGrade.x3,
                votesByGrade.x4,
                votesByGrade.x5,
            )
        } else {
            emptyImmutableList()
        }
    }

    val summedVotes by lazy {
        votesByGrade?.totalVotes
    }

    val maxValue by lazy {
        summedVotes?.let {
            votesList.maxOf { it / it.toFloat() }
        }
    }
}

fun List<AlbumStats>.globalAverage(): Float = (sumOf { it.averageRating } / size).toFloat()
