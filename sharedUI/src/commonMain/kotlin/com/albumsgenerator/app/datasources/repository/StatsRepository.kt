package com.albumsgenerator.app.datasources.repository

import co.touchlab.kermit.Logger
import com.albumsgenerator.app.datasources.cache.daos.StatsDao
import com.albumsgenerator.app.datasources.cache.entities.StatEntity
import com.albumsgenerator.app.datasources.network.AlbumGeneratorService
import com.albumsgenerator.app.di.modules.IO
import com.albumsgenerator.app.domain.models.AlbumStats
import com.albumsgenerator.app.domain.models.AlbumType
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface StatsRepository {
    fun statsFlow(): Flow<List<AlbumStats>>
    fun userStatsFlow(): Flow<List<AlbumStats>>
    fun statsForAlbum(name: String): Flow<AlbumStats?>
    fun statsForArtist(artist: String): Flow<List<AlbumStats>>
    fun statsForGenre(
        genre: String,
        limit: Int,
    ): Flow<List<AlbumStats>>
    fun statsByYear(
        year: String,
        limit: Int,
    ): Flow<List<AlbumStats>>
    suspend fun fetchAndStoreStats()
}

@ContributesBinding(AppScope::class)
@Suppress("Unused")
class RealStatsRepository(
    @param:IO private val ioDispatcher: CoroutineDispatcher,
    val albumGeneratorService: AlbumGeneratorService,
    val statsDao: StatsDao,
) : StatsRepository {
    private val logger = Logger.withTag("RealStatsRepository")

    override fun statsFlow(): Flow<List<AlbumStats>> {
        logger.i { "[StatsFlow] Fetching the album stats" }
        return statsDao
            .streamAllByType(type = AlbumType.OFFICIAL.ordinal)
            .map { entities -> entities.map { it.toDomain() } }
            .onEach {
                logger.d { "[StatsFlow] Successfully fetched ${it.size} stats" }
            }
    }

    override fun userStatsFlow(): Flow<List<AlbumStats>> {
        logger.i { "[UserStatsFlow] Fetching the user album stats" }
        return statsDao
            .streamAllByType(type = AlbumType.USER.ordinal)
            .map { entities -> entities.map { it.toDomain() } }
            .onEach {
                logger.d { "[UserStatsFlow] Successfully fetched ${it.size} user stats" }
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

    override fun statsByYear(
        year: String,
        limit: Int,
    ): Flow<List<AlbumStats>> {
        logger.i { "[StatsFlow] Fetching the album stats for year $year" }
        return statsDao
            .getByYear(year = year, limit = limit)
            .map { entities -> entities.map { it.toDomain() } }
            .onEach {
                logger.d { "[StatsFlow] Successfully fetched ${it.size} stats for year $year" }
            }
    }

    override suspend fun fetchAndStoreStats() {
        withContext(ioDispatcher) {
            launch { fetchAndStoreAlbumStats() }
            launch { fetchAndStoreUserStats() }
        }
    }

    private suspend fun fetchAndStoreAlbumStats() {
        logger.i { "[FetchAndStoreAlbumStats] Fetching the stats" }
        try {
            val stats = albumGeneratorService.getStats()
            statsDao.insertAll(stats.map { StatEntity.fromDomain(it) })
            logger.d { "[FetchAndStoreAlbumStats] Successfully inserted ${stats.size} stats" }
        } catch (e: Exception) {
            logger.w(e) { "[FetchAndStoreAlbumStats] Exception during fetchAndStoreAlbumStats: $e" }
        }
    }

    private suspend fun fetchAndStoreUserStats() {
        logger.i { "[FetchAndStoreUserStats] Fetching the stats" }
        try {
            val stats = albumGeneratorService.getUserAlbumStats()
            statsDao.insertAll(stats.map { StatEntity.fromDomain(it) })
            logger.i { "[FetchAndStoreUserStats] Successfully inserted ${stats.size} user stats" }
        } catch (e: Exception) {
            logger.w(e) { "[FetchAndStoreUserStats] Exception during fetchAndStoreUserStats: $e" }
        }
    }
}
