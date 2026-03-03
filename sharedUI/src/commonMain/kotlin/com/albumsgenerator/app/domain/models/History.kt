package com.albumsgenerator.app.domain.models

import androidx.compose.runtime.Immutable
import kotlin.time.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Immutable
data class History(
    val album: Album,
    val generatedAt: Instant,
    val globalRating: Double,
    val rating: String?,
    val revealedAlbum: Boolean,
    val review: String?,
    val index: Int,
) {
    val generatedAtDate by lazy {
        generatedAt.toLocalDateTime(TimeZone.currentSystemDefault()).date
    }

    val hasRating: Boolean get() = rating != null && rating != SKIPPED_TAG

    val ratingDiff by lazy {
        val parsedRating = rating?.toIntOrNull()
        if (hasRating && parsedRating != null) {
            (parsedRating - globalRating).toFloat()
        } else {
            0.0f
        }
    }

    companion object {
        const val SKIPPED_TAG = "did-not-listen"
    }
}

fun List<History>.averageRating() =
    sumOf { it.rating?.toIntOrNull() ?: 0 }.toFloat() / size.toFloat()
