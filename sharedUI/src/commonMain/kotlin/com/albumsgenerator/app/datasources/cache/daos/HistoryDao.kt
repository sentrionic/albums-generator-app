package com.albumsgenerator.app.datasources.cache.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.albumsgenerator.app.datasources.cache.entities.HistoryEntity
import com.albumsgenerator.app.datasources.cache.entities.HistoryWithAlbum
import com.albumsgenerator.app.domain.models.History
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Transaction
    @Query("SELECT * FROM histories ORDER BY generated_at DESC")
    fun getAllDesc(): Flow<List<HistoryWithAlbum>>

    @Transaction
    @Query("SELECT * FROM histories WHERE albumId = :id")
    fun getByIdFlow(id: String): Flow<HistoryWithAlbum?>

    @Transaction
    @Query(
        "SELECT h.* FROM histories h JOIN albums a ON a.uuid = h.albumId WHERE a.artist = :artist AND a.name = :name",
    )
    fun getByAlbumNameAndArtistFlow(
        name: String,
        artist: String,
    ): Flow<HistoryWithAlbum?>

    @Transaction
    @Query("SELECT * FROM histories WHERE albumId = :id")
    suspend fun getById(id: String): HistoryWithAlbum?

    @Transaction
    @Query(
        "SELECT h.* FROM histories h JOIN albums a ON a.uuid = h.albumId WHERE a.artist = :artist",
    )
    suspend fun getArtistAlbums(artist: String): List<HistoryWithAlbum>

    @Query("SELECT 1 FROM histories WHERE rating IS NULL ORDER BY generated_at ASC LIMIT 1")
    suspend fun hasUnratedAlbum(): Boolean

    @Query("SELECT count(*) FROM histories")
    suspend fun getTotalHistoryCount(): Int

    @Query("SELECT count(*) FROM histories WHERE rating IN ('1', '2', '3', '4', '5')")
    suspend fun getRatedAlbumsCount(): Int

    @Query(
        "SELECT AVG(rating) FROM histories WHERE rating IS NOT NULL AND rating != '${History.SKIPPED_TAG}'",
    )
    suspend fun getAverageRating(): Float

    @Transaction
    @Query("SELECT * FROM histories WHERE rating = :rating ORDER BY generated_at ASC")
    suspend fun getAlbumsByRating(rating: String): List<HistoryWithAlbum>

    @Transaction
    @Query(
        "SELECT h.* FROM histories h JOIN albums a ON a.uuid = h.albumId WHERE a.release_date = :year ORDER BY h.global_rating DESC LIMIT :limit",
    )
    fun getAlbumsByYear(
        year: String,
        limit: Int,
    ): Flow<List<HistoryWithAlbum>>

    @Upsert
    suspend fun insertAll(histories: List<HistoryEntity>)

    @Transaction
    @Query(
        """
            SELECT h.* FROM histories h
            JOIN albums a ON a.uuid = h.albumId
            WHERE a.genres = :genre
            OR a.genres LIKE :genreBoth
            OR a.genres LIKE :genreLeft
            OR a.genres LIKE :genreRight
            ORDER BY h.global_rating DESC
            LIMIT :limit
        """,
    )
    fun getByGenre(
        genre: String,
        genreLeft: String,
        genreRight: String,
        genreBoth: String,
        limit: Int,
    ): Flow<List<HistoryWithAlbum>>
}
