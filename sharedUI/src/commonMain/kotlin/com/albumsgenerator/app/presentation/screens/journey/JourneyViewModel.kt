package com.albumsgenerator.app.presentation.screens.journey

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.albumsgenerator.app.datasources.repository.HistoryRepository
import com.albumsgenerator.app.datasources.repository.StatsRepository
import com.albumsgenerator.app.di.modules.Default
import com.albumsgenerator.app.domain.core.Coroutines
import com.albumsgenerator.app.domain.core.DataState
import com.albumsgenerator.app.domain.core.immutableMap
import com.albumsgenerator.app.domain.core.immutableSortedBy
import com.albumsgenerator.app.domain.core.immutableSortedByDescending
import com.albumsgenerator.app.domain.models.AlbumStats
import com.albumsgenerator.app.domain.models.History
import com.albumsgenerator.app.domain.models.averageRating
import com.albumsgenerator.app.domain.models.globalAverage
import com.albumsgenerator.app.presentation.utils.capitalize
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext

@Suppress("MagicNumber")
@ContributesIntoMap(AppScope::class)
@ViewModelKey
@Inject
class JourneyViewModel(
    @param:Default private val defaultDispatcher: CoroutineDispatcher,
    historyRepository: HistoryRepository,
    statsRepository: StatsRepository,
) : ViewModel() {
    val state = combine(
        historyRepository.historiesWithRating(),
        statsRepository.statsFlow(),
    ) { histories, stats ->
        DataState.Success(
            JourneyState(
                byDecade = getByDecade(histories, stats),
                byGenre = getByGenre(histories, stats),
                byOrigin = getByOrigin(histories, stats),
                byStyles = getByStyles(histories, stats),
                byYears = getByYears(histories),
                aboveAverageOutliers = aboveAverageOutliers(histories),
                belowAverageOutliers = belowAverageOutliers(histories),
            ),
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(Coroutines.SUBSCRIPTION_TIMEOUT_MS),
        initialValue = DataState.Loading(),
    )

    private suspend fun getByDecade(
        histories: List<History>,
        stats: List<AlbumStats>,
    ): ImmutableList<JourneyState.Item> = withContext(defaultDispatcher) {
        val items = groupUserHistoryByDecade(histories)
        val decadeToGlobal = groupGlobalStatsByDecade(stats)

        return@withContext items
            .mapNotNull { (decade, albums) ->
                val relatedStat = decadeToGlobal[decade] ?: return@mapNotNull null
                JourneyState.Item(
                    label = "$decade",
                    albumsCount = albums.size,
                    average = albums.averageRating(),
                    global = relatedStat.globalAverage(),
                )
            }
            .immutableSortedByDescending {
                it.average
            }
    }

    private fun groupUserHistoryByDecade(histories: List<History>): Map<Int, List<History>> {
        val items = mutableMapOf<Int, List<History>>()

        for (history in histories) {
            val album = history.album
            val year = album.releaseDate.toIntOrNull()
            when (year) {
                in 1950..<1960 -> items[1950] = items.getOrPut(1950) { emptyList() } + history
                in 1960..<1970 -> items[1960] = items.getOrPut(1960) { emptyList() } + history
                in 1970..<1980 -> items[1970] = items.getOrPut(1970) { emptyList() } + history
                in 1980..<1990 -> items[1980] = items.getOrPut(1980) { emptyList() } + history
                in 1990..<2000 -> items[1990] = items.getOrPut(1990) { emptyList() } + history
                in 2000..<2010 -> items[2000] = items.getOrPut(2000) { emptyList() } + history
                in 2010..<2020 -> items[2010] = items.getOrPut(2010) { emptyList() } + history
                in 2020..<2030 -> items[2020] = items.getOrPut(2020) { emptyList() } + history
            }
        }

        return items
    }

    private fun groupGlobalStatsByDecade(stats: List<AlbumStats>): Map<Int, List<AlbumStats>> {
        val decadeToGlobal = mutableMapOf<Int, List<AlbumStats>>()

        for (stat in stats) {
            val year = stat.releaseDate.toIntOrNull()
            when (year) {
                in 1950..<1960 -> decadeToGlobal[1950] =
                    decadeToGlobal.getOrPut(1950) { emptyList() } + stat

                in 1960..<1970 -> decadeToGlobal[1960] =
                    decadeToGlobal.getOrPut(1960) { emptyList() } + stat

                in 1970..<1980 -> decadeToGlobal[1970] =
                    decadeToGlobal.getOrPut(1970) { emptyList() } + stat

                in 1980..<1990 -> decadeToGlobal[1980] =
                    decadeToGlobal.getOrPut(1980) { emptyList() } + stat

                in 1990..<2000 -> decadeToGlobal[1990] =
                    decadeToGlobal.getOrPut(1990) { emptyList() } + stat

                in 2000..<2010 -> decadeToGlobal[2000] =
                    decadeToGlobal.getOrPut(2000) { emptyList() } + stat

                in 2010..<2020 -> decadeToGlobal[2010] =
                    decadeToGlobal.getOrPut(2010) { emptyList() } + stat

                in 2020..<2030 -> decadeToGlobal[2020] =
                    decadeToGlobal.getOrPut(2020) { emptyList() } + stat
            }
        }

        return decadeToGlobal
    }

    private suspend fun getByGenre(
        histories: List<History>,
        stats: List<AlbumStats>,
    ): ImmutableList<JourneyState.Item> = withContext(defaultDispatcher) {
        val items = mutableMapOf<String, List<History>>()
        val genreToGlobal = mutableMapOf<String, List<AlbumStats>>()

        for (history in histories) {
            val album = history.album
            for (genre in album.genres) {
                items[genre] = items.getOrPut(genre) { emptyList() } + history
            }
        }

        for (stat in stats) {
            for (genre in stat.genres) {
                genreToGlobal[genre] = genreToGlobal.getOrPut(genre) { emptyList() } + stat
            }
        }

        return@withContext items
            .mapNotNull { (genre, albums) ->
                val relatedStat = genreToGlobal[genre] ?: return@mapNotNull null
                JourneyState.Item(
                    label = genre.capitalize(),
                    albumsCount = albums.size,
                    average = albums.averageRating(),
                    global = relatedStat.globalAverage(),
                )
            }
            .immutableSortedByDescending {
                it.average
            }
    }

    private suspend fun getByOrigin(
        histories: List<History>,
        stats: List<AlbumStats>,
    ): ImmutableList<JourneyState.Item> = withContext(defaultDispatcher) {
        val groupedHistories = histories.groupBy { it.album.artistOrigin.orEmpty() }
        val groupedStats = stats.groupBy { it.artistOrigin }

        return@withContext groupedHistories
            .mapNotNull { (origin, albums) ->
                val relatedStat = groupedStats[origin] ?: return@mapNotNull null
                JourneyState.Item(
                    label = origin,
                    albumsCount = albums.size,
                    average = albums.averageRating(),
                    global = relatedStat.globalAverage(),
                )
            }
            .immutableSortedByDescending {
                it.average
            }
    }

    private suspend fun getByStyles(
        histories: List<History>,
        stats: List<AlbumStats>,
    ): ImmutableList<JourneyState.ItemWithAlbums> = withContext(defaultDispatcher) {
        val items = mutableMapOf<String, List<History>>()

        for (stat in stats) {
            val history = histories.firstOrNull { it.album.name == stat.name } ?: continue
            for (genre in stat.styles) {
                items[genre] = items.getOrPut(genre) { emptyList() } + history
            }
        }

        return@withContext items
            .filter { it.value.size > 2 }
            .mapNotNull { (style, albums) ->
                JourneyState.ItemWithAlbums(
                    label = style.capitalize(),
                    average = albums.averageRating(),
                    albums = albums.sortedBy { it.generatedAt }.immutableMap { it.album },
                )
            }
            .immutableSortedByDescending {
                it.average
            }
    }

    private suspend fun getByYears(
        histories: List<History>,
    ): ImmutableList<JourneyState.ItemWithAlbums> = withContext(defaultDispatcher) {
        val groupedHistories = histories.groupBy { it.album.releaseDate }

        return@withContext groupedHistories
            .mapNotNull { (year, albums) ->
                JourneyState.ItemWithAlbums(
                    label = year,
                    average = albums.averageRating(),
                    albums = albums.sortedBy { it.generatedAt }.immutableMap { it.album },
                )
            }
            .immutableSortedByDescending {
                it.average
            }
    }

    private suspend fun aboveAverageOutliers(histories: List<History>): ImmutableList<History> =
        withContext(defaultDispatcher) {
            histories
                .filter {
                    it.ratingDiff > RATING_THRESHOLD
                }
                .immutableSortedByDescending { it.ratingDiff }
        }

    private suspend fun belowAverageOutliers(histories: List<History>): ImmutableList<History> =
        withContext(defaultDispatcher) {
            histories
                .filter {
                    it.ratingDiff < (RATING_THRESHOLD * -1)
                }
                .immutableSortedBy { it.ratingDiff }
        }

    private companion object {
        private const val RATING_THRESHOLD = 1.8
    }
}
