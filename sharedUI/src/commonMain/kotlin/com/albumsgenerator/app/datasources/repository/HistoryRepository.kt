package com.albumsgenerator.app.datasources.repository

import co.touchlab.kermit.Logger
import com.albumsgenerator.app.datasources.cache.daos.HistoryDao
import com.albumsgenerator.app.domain.models.History
import com.albumsgenerator.app.domain.values.Rating
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

interface HistoryRepository {
    fun historiesFlow(): Flow<List<History>>
    fun historyFlow(albumId: String): Flow<History?>
    fun historyFlow(
        name: String,
        artist: String,
    ): Flow<History?>

    fun genreHistories(
        genre: String,
        limit: Int,
    ): Flow<List<History>>
    suspend fun hasUnratedHistory(): Boolean
    suspend fun historyCount(): Int
    suspend fun ratedAlbumsCount(): Int
    suspend fun averageRating(): Float
    suspend fun historiesByRating(rating: Rating): List<History>
    suspend fun historiesByYear(
        year: String,
        limit: Int,
    ): List<History>
    suspend fun artistHistories(artist: String): List<History>
}

@ContributesBinding(AppScope::class)
@Inject
@Suppress("Unused")
class RealHistoryRepository(val historyDao: HistoryDao) : HistoryRepository {
    private val logger = Logger.withTag("RealHistoryRepository")

    override fun historiesFlow(): Flow<List<History>> {
        logger.i { "[HistoriesFlow] Fetching the current user's history." }
        return historyDao
            .getAllDesc()
            .map { histories ->
                histories
                    .map { (history, album) ->
                        history.toDomain(album = album.toDomain())
                    }
            }
            .onEach {
                logger.d { "[HistoriesFlow] Successfully fetched ${it.size} entries." }
            }
    }

    override fun historyFlow(albumId: String): Flow<History?> {
        val postfix = "the history for album $albumId."
        logger.i { "[HistoriesFlow] Fetching $postfix" }
        return historyDao
            .getByIdFlow(albumId)
            .map { it?.toDomain() }
            .onEach {
                logger.d { "[HistoriesFlow] Successfully fetched $postfix" }
            }
    }

    override fun historyFlow(
        name: String,
        artist: String,
    ): Flow<History?> {
        val postfix = "the history for album $name and artist $artist."
        logger.i { "[HistoriesFlow] Fetching $postfix" }
        return historyDao
            .getByAlbumNameAndArtistFlow(name, artist)
            .map { it?.toDomain() }
            .onEach {
                logger.d { "[HistoriesFlow] Successfully fetched $postfix" }
            }
    }

    override fun genreHistories(
        genre: String,
        limit: Int,
    ): Flow<List<History>> {
        logger.i { "[HistoriesFlow] Fetching the current user's histories for $genre." }
        return historyDao
            .getByGenre(
                genre = genre,
                genreLeft = "%,$genre",
                genreRight = "$genre,%",
                genreBoth = "%,$genre,%",
                limit = limit,
            )
            .map { histories ->
                histories
                    .map { (history, album) ->
                        history.toDomain(album = album.toDomain())
                    }
            }
            .onEach {
                logger.d { "[HistoriesFlow] Successfully fetched ${it.size} entries for $genre." }
            }
    }

    override suspend fun hasUnratedHistory(): Boolean {
        logger.i { "[HasUnratedHistory] Checking if the user has an unrated album." }
        val result = historyDao.hasUnratedAlbum()
        logger.d { "[HasUnratedHistory] Has unrated album: $result" }
        return result
    }

    override suspend fun historyCount(): Int {
        logger.i { "[HistoryCount] Fetching the user's total history count." }
        val result = historyDao.getTotalHistoryCount()
        logger.d { "[HistoryCount] History count: $result" }
        return result
    }

    override suspend fun ratedAlbumsCount(): Int {
        logger.i { "[RatedAlbumsCount] Fetching the user's rated history count." }
        val result = historyDao.getRatedAlbumsCount()
        logger.d { "[RatedAlbumsCount] Rated history count: $result" }
        return result
    }

    override suspend fun averageRating(): Float {
        logger.i { "[AverageRating] Fetching the user's average rating." }
        val result = historyDao.getAverageRating()
        logger.d { "[AverageRating] Average rating: $result" }
        return result
    }

    override suspend fun historiesByRating(rating: Rating): List<History> {
        logger.i { "[HistoriesByRating] Fetching albums with rating ${rating.value}" }
        val result = historyDao.getAlbumsByRating(rating.value).map { it.toDomain() }
        logger.d { "[HistoriesByRating] Fetched ${result.size} albums with rating ${rating.value}" }
        return result
    }

    override suspend fun historiesByYear(
        year: String,
        limit: Int,
    ): List<History> {
        logger.i { "[HistoriesByYear] Fetching up to $limit albums with release date $year" }
        val result = historyDao.getAlbumsByYear(year, limit).map { it.toDomain() }
        logger.d {
            "[HistoriesByRating] Fetched ${result.size} albums with with release date $year"
        }
        return result
    }

    override suspend fun artistHistories(artist: String): List<History> {
        logger.i { "[ArtistHistories] Fetching albums for artist $artist" }
        val result = historyDao.getArtistAlbums(artist).map { it.toDomain() }
        logger.d { "[ArtistHistories] Fetched ${result.size} albums for artist $artist" }
        return result
    }
}
