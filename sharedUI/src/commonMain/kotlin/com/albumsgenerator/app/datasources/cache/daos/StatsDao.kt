package com.albumsgenerator.app.datasources.cache.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.albumsgenerator.app.datasources.cache.entities.StatEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StatsDao {
    @Query("SELECT * FROM stats ORDER BY average_rating DESC")
    fun getAll(): Flow<List<StatEntity>>

    @Query("SELECT * FROM stats WHERE name = :name")
    fun getByAlbumName(name: String): Flow<StatEntity?>

    @Query("SELECT * FROM stats WHERE artist = :artist")
    fun getByArtist(artist: String): Flow<List<StatEntity>>

    @Query(
        """
            SELECT * FROM stats
            WHERE genres = :genre
            OR genres LIKE :genreBoth
            OR genres LIKE :genreLeft
            OR genres LIKE :genreRight
            ORDER BY average_rating DESC
            LIMIT :limit
        """,
    )
    fun getByGenre(
        genre: String,
        genreLeft: String,
        genreRight: String,
        genreBoth: String,
        limit: Int,
    ): Flow<List<StatEntity>>

    @Query(
        """
            SELECT * FROM stats
            WHERE release_date = :year
            ORDER BY average_rating DESC
            LIMIT :limit
        """,
    )
    fun getByYear(
        year: String,
        limit: Int,
    ): Flow<List<StatEntity>>

    @Upsert
    suspend fun insertAll(stats: List<StatEntity>)
}
