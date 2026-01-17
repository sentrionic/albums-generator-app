package com.albumsgenerator.app.datasources.repository

import co.touchlab.kermit.Logger
import com.albumsgenerator.app.datasources.cache.daos.AlbumDao
import com.albumsgenerator.app.datasources.cache.entities.AlbumEntity
import com.albumsgenerator.app.datasources.network.WikipediaService
import com.albumsgenerator.app.domain.models.Album
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

interface AlbumRepository {
    fun albumFlow(id: String): Flow<Album?>
    fun albumFlow(
        name: String,
        artist: String,
    ): Flow<Album?>
    suspend fun getAndStoreAlbum(id: String)
    suspend fun getAndStoreAlbum(
        name: String,
        artist: String,
    )
}

@ContributesBinding(AppScope::class)
@Inject
@Suppress("Unused")
class RealAlbumRepository(
    private val wikipediaService: WikipediaService,
    private val albumDao: AlbumDao,
) : AlbumRepository {
    private val logger = Logger.withTag("RealAlbumRepository")

    override fun albumFlow(id: String): Flow<Album?> {
        logger.i { "[AlbumFlow] Fetching the album for id $id" }
        return albumDao.getByIdFlow(id).map { it?.toDomain() }.onEach {
            if (it == null) {
                logger.i { "[AlbumFlow] Could not find the album for id $id" }
            } else {
                logger.i { "[AlbumFlow] Successfully emitted the album for id $id" }
            }
        }
    }

    override fun albumFlow(
        name: String,
        artist: String,
    ): Flow<Album?> {
        val postFix = "the album for name $name and artist $artist"
        logger.i { "[AlbumFlow] Fetching $postFix" }
        return albumDao.getByNameAndArtist(name, artist).map { it?.toDomain() }.onEach {
            if (it == null) {
                logger.i { "[AlbumFlow] Could not find $postFix" }
            } else {
                logger.i { "[AlbumFlow] Successfully emitted $postFix" }
            }
        }
    }

    override suspend fun getAndStoreAlbum(id: String) {
        val album = albumDao.getById(id)?.toDomain() ?: return
        getAlbumSummary(album)
    }

    override suspend fun getAndStoreAlbum(
        name: String,
        artist: String,
    ) {
        val album = albumDao.getByNameAndArtist(name, artist).firstOrNull()?.toDomain() ?: return
        getAlbumSummary(album)
    }

    private suspend fun getAlbumSummary(album: Album) {
        val postFix = "for the album ${album.uuid}"
        logger.i { "[GetAlbumSummary] Fetching the summary $postFix" }
        try {
            val summary = wikipediaService.getSummary(
                album.wikipediaUrl.substringAfterLast("wiki/"),
            )
            logger.i { "[GetAlbumSummary] Successfully fetched the summary $postFix" }
            albumDao.updateAlbum(AlbumEntity.fromDomain(album.copy(summary = summary)))
        } catch (e: Exception) {
            logger.w(e) { "[GetAlbumSummary] Could not fetch the summary $postFix" }
        }
    }
}
