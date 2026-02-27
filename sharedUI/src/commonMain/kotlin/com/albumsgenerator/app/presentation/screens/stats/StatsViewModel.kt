package com.albumsgenerator.app.presentation.screens.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.albumsgenerator.app.datasources.repository.HistoryRepository
import com.albumsgenerator.app.datasources.repository.PreferencesRepository
import com.albumsgenerator.app.datasources.repository.StatsRepository
import com.albumsgenerator.app.di.modules.IO
import com.albumsgenerator.app.domain.core.Constants
import com.albumsgenerator.app.domain.core.Coroutines
import com.albumsgenerator.app.domain.core.DataState
import com.albumsgenerator.app.domain.models.AlbumStats
import com.albumsgenerator.app.domain.models.SpoilerMode
import com.albumsgenerator.app.domain.models.globalAverage
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@ContributesIntoMap(AppScope::class)
@ViewModelKey(StatsViewModel::class)
@Inject
class StatsViewModel(
    @param:IO private val ioDispatcher: CoroutineDispatcher,
    preferencesRepository: PreferencesRepository,
    historyRepository: HistoryRepository,
    private val statsRepository: StatsRepository,
) : ViewModel() {
    val state = combine(
        statsRepository.statsFlow(),
        historyRepository.historiesFlow(),
        preferencesRepository.userData,
    ) { stats, histories, userData ->
        if (stats.isEmpty()) {
            return@combine DataState.Loading()
        }

        val totalVotes = stats.sumOf { it.votes }
        val statsSortedByControversialScore = stats.sortedByDescending {
            it.controversialScore
        }

        val previousAlbumNames = histories.map { it.album.name }

        fun List<AlbumStats>.takeVisible() = when (userData.spoilerMode) {
            SpoilerMode.HIDDEN -> this.filter { it.name in previousAlbumNames }
            else -> this
        }

        DataState.Success(
            StatsScreenState(
                topAlbums = stats.take(Constants.LIMIT).takeVisible(),
                bottomAlbums = stats.takeLast(Constants.LIMIT).reversed().takeVisible(),
                mostControversial = statsSortedByControversialScore.take(
                    Constants.LIMIT,
                ).takeVisible(),
                leastControversial = statsSortedByControversialScore.takeLast(
                    Constants.LIMIT,
                ).takeVisible()
                    .reversed(),
                votes = totalVotes,
                averageRating = stats.globalAverage(),
                spoilerMode = userData.spoilerMode,
                previousAlbumNames = previousAlbumNames,
            ),
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(Coroutines.SUBSCRIPTION_TIMEOUT_MS),
            initialValue = DataState.Loading(),
        )

    init {
        viewModelScope.launch(ioDispatcher) {
            statsRepository.fetchAndStoreStats()
        }
    }
}
