package com.albumsgenerator.app.datasources.cache.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.albumsgenerator.app.domain.models.Album
import com.albumsgenerator.app.domain.models.History
import kotlin.time.Instant

@Entity(tableName = "histories")
data class HistoryEntity(
    @PrimaryKey
    val albumId: String,
    @ColumnInfo("generated_at")
    val generatedAt: String,
    @ColumnInfo("global_rating")
    val globalRating: Double,
    @ColumnInfo("rating", index = true)
    val rating: String?,
    @ColumnInfo("revealed_album")
    val revealedAlbum: Boolean,
    @ColumnInfo("review")
    val review: String?,
    @ColumnInfo("index")
    val index: Int,
) {
    fun toDomain(album: Album): History = History(
        album = album,
        generatedAt = Instant.parse(generatedAt),
        globalRating = globalRating,
        rating = rating,
        revealedAlbum = revealedAlbum,
        review = review,
        index = index,
    )

    companion object {
        fun fromDomain(history: History): HistoryEntity = HistoryEntity(
            albumId = history.album.uuid,
            generatedAt = history.generatedAt.toString(),
            globalRating = history.globalRating,
            rating = history.rating,
            revealedAlbum = history.revealedAlbum,
            review = history.review,
            index = history.index,
        )
    }
}
