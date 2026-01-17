package com.albumsgenerator.app.datasources.network.dtos

import com.albumsgenerator.app.domain.models.History
import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HistoryDto(
    @SerialName("album")
    val album: AlbumDto,
    @SerialName("generatedAt")
    val generatedAt: String,
    @SerialName("globalRating")
    val globalRating: Double,
    @SerialName("rating")
    val rating: String? = null,
    @SerialName("revealedAlbum")
    val revealedAlbum: Boolean,
    @SerialName("review")
    val review: String? = null,
) {
    fun toDomain(index: Int): History = History(
        album = album.toDomain(),
        generatedAt = Instant.parse(generatedAt),
        globalRating = globalRating,
        rating = rating,
        revealedAlbum = revealedAlbum,
        review = review,
        index = index,
    )
}
