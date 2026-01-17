package com.albumsgenerator.app.datasources.repository

import co.touchlab.kermit.Logger
import com.albumsgenerator.app.datasources.cache.daos.StatsDao
import com.albumsgenerator.app.datasources.cache.entities.StatEntity
import com.albumsgenerator.app.datasources.network.AlbumGeneratorService
import com.albumsgenerator.app.domain.models.AlbumStats
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

interface StatsRepository {
    fun statsFlow(): Flow<List<AlbumStats>>
    fun statsForAlbum(name: String): Flow<AlbumStats?>
    fun statsForArtist(artist: String): Flow<List<AlbumStats>>
    fun statsForGenre(
        genre: String,
        limit: Int,
    ): Flow<List<AlbumStats>>
    suspend fun fetchAndStoreStats()
}

@ContributesBinding(AppScope::class)
@Inject
@Suppress("Unused")
class RealStatsRepository(
    val albumGeneratorService: AlbumGeneratorService,
    val statsDao: StatsDao,
) : StatsRepository {
    private val logger = Logger.withTag("RealStatsRepository")

    override fun statsFlow(): Flow<List<AlbumStats>> {
        logger.i { "[StatsFlow] Fetching the album stats" }
        return statsDao
            .getAll()
            .map { entities -> entities.map { it.toDomain() } }
            .onEach {
                logger.d { "[StatsFlow] Successfully fetched ${it.size} stats" }
            }
    }

    override fun statsForAlbum(name: String): Flow<AlbumStats?> {
        logger.i { "[StatsForAlbum] Fetching the stats for $name" }
        return statsDao.getByAlbumName(name)
            .map { it?.toDomain() }
            .onEach {
                if (it == null) {
                    logger.d { "[StatsForAlbum] Could not find stats for the album $name" }
                } else {
                    logger.d { "[StatsForAlbum] Successfully emitted the stats for album $name" }
                }
            }
    }

    override fun statsForArtist(artist: String): Flow<List<AlbumStats>> {
        logger.i { "[StatsFlow] Fetching the album stats for artist $artist" }
        return statsDao
            .getByArtist(artist)
            .map { entities -> entities.map { it.toDomain() } }
            .onEach {
                logger.d { "[StatsFlow] Successfully fetched ${it.size} stats" }
            }
    }

    override fun statsForGenre(
        genre: String,
        limit: Int,
    ): Flow<List<AlbumStats>> {
        logger.i { "[StatsFlow] Fetching the album stats for genre $genre" }
        return statsDao
            .getByGenre(
                genre = genre,
                genreLeft = "%,$genre",
                genreRight = "$genre,%",
                genreBoth = "%,$genre,%",
                limit = limit,
            )
            .map { entities -> entities.map { it.toDomain() } }
            .onEach {
                logger.d { "[StatsFlow] Successfully fetched ${it.size} stats" }
            }
    }

    override suspend fun fetchAndStoreStats() {
        logger.i { "[FetchAndStoreStats] Fetching the stats" }
        try {
            val stats = albumGeneratorService.getStats()
            statsDao.insertAll(stats.map { StatEntity.fromDomain(it) })
            logger.i { "[FetchAndStoreStats] Successfully inserted ${stats.size} stats" }
        } catch (e: Exception) {
            logger.w(e) { "[FetchAndStoreStats] Exception during fetchAndStoreStats: $e" }
        }
    }
}
