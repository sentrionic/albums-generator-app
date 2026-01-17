package com.albumsgenerator.app.datasources.cache.entities

import androidx.room.Embedded
import androidx.room.Relation

data class HistoryWithAlbum(
    @Embedded val history: HistoryEntity,
    @Relation(
        parentColumn = "albumId",
        entityColumn = "uuid",
    )
    val album: AlbumEntity,
) {
    fun toDomain() = history.toDomain(album.toDomain())
}
