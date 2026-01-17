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

    companion object {
        const val SKIPPED_TAG = "did-not-listen"
    }
}
