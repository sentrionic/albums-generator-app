package com.albumsgenerator.app.datasources.cache.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.albumsgenerator.app.datasources.cache.entities.AlbumEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumDao {
    @Query("SELECT * FROM albums")
    suspend fun getAll(): List<AlbumEntity>

    @Query("SELECT * FROM albums WHERE uuid = :id")
    suspend fun getById(id: String): AlbumEntity?

    @Query("SELECT * FROM albums WHERE uuid = :id")
    fun getByIdFlow(id: String): Flow<AlbumEntity?>

    @Query("SELECT * FROM albums WHERE name = :name AND artist = :artist")
    fun getByNameAndArtist(
        name: String,
        artist: String,
    ): Flow<AlbumEntity?>

    @Query("SELECT * FROM albums WHERE artist = :name")
    suspend fun getByArtist(name: String): List<AlbumEntity>

    @Upsert
    suspend fun insertAll(albums: List<AlbumEntity>)

    @Update
    suspend fun updateAlbum(albumEntity: AlbumEntity)
}
