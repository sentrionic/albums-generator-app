package com.albumsgenerator.app.presentation.screens.stats

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.albumsgenerator.app.datasources.repository.HistoryRepository
import com.albumsgenerator.app.datasources.repository.PreferencesRepository
import com.albumsgenerator.app.datasources.repository.StatsRepository
import com.albumsgenerator.app.domain.core.Constants
import com.albumsgenerator.app.domain.core.Coroutines
import com.albumsgenerator.app.domain.core.DataState
import com.albumsgenerator.app.domain.core.immutableFilter
import com.albumsgenerator.app.domain.core.immutableMap
import com.albumsgenerator.app.domain.models.AlbumStats
import com.albumsgenerator.app.domain.models.AlbumType
import com.albumsgenerator.app.domain.models.SpoilerMode
import com.albumsgenerator.app.domain.models.globalAverage
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metrox.viewmodel.ViewModelAssistedFactory
import dev.zacsweers.metrox.viewmodel.ViewModelAssistedFactoryKey
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@AssistedInject
class StatsViewModel(
    @Assisted private val savedStateHandle: SavedStateHandle,
    preferencesRepository: PreferencesRepository,
    historyRepository: HistoryRepository,
    private val statsRepository: StatsRepository,
) : ViewModel() {
    private val displayTypeFlow = savedStateHandle
        .getStateFlow(STATS_TYPE_KEY, AlbumType.OFFICIAL.ordinal)
        .map { type ->
            AlbumType.entries.firstOrNull { it.ordinal == type } ?: AlbumType.OFFICIAL
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    private val statsFlow = displayTypeFlow.flatMapLatest { type ->
        when (type) {
            AlbumType.OFFICIAL -> statsRepository.statsFlow()
            AlbumType.USER -> statsRepository.userStatsFlow()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state = combine(
        statsFlow,
        historyRepository.historiesFlow(),
        preferencesRepository.userData,
        displayTypeFlow,
    ) { stats, histories, userData, type ->
        if (stats.isEmpty()) {
            return@combine DataState.Loading()
        }

        val totalVotes = stats.sumOf { it.votes }
        val statsSortedByControversialScore = stats.sortedByDescending {
            it.controversialScore
        }

        val previousAlbumNames = histories.immutableMap { it.album.name }

        fun List<AlbumStats>.takeVisible() = when (userData.spoilerMode) {
            SpoilerMode.HIDDEN -> this.immutableFilter { it.name in previousAlbumNames }
            else -> this.toImmutableList()
        }

        DataState.Success(
            StatsScreenState(
                totalAlbums = stats.size,
                topAlbums = stats.take(Constants.LIMIT).takeVisible(),
                bottomAlbums = stats.takeLast(Constants.LIMIT).reversed().takeVisible(),
                mostControversial = statsSortedByControversialScore.take(
                    Constants.LIMIT,
                ).takeVisible(),
                leastControversial = statsSortedByControversialScore.takeLast(
                    Constants.LIMIT,
                ).takeVisible()
                    .reversed().toImmutableList(),
                votes = totalVotes,
                averageRating = stats.globalAverage(),
                spoilerMode = userData.spoilerMode,
                previousAlbumNames = previousAlbumNames,
                displayType = type,
            ),
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(Coroutines.SUBSCRIPTION_TIMEOUT_MS),
            initialValue = DataState.Loading(),
        )

    init {
        viewModelScope.launch {
            statsRepository.fetchAndStoreStats()
        }
    }

    fun toggleDisplayType(type: AlbumType) {
        savedStateHandle[STATS_TYPE_KEY] = type.ordinal
    }

    @AssistedFactory
    @ViewModelAssistedFactoryKey(StatsViewModel::class)
    @ContributesIntoMap(AppScope::class)
    interface Factory : ViewModelAssistedFactory {
        override fun create(extras: CreationExtras): ViewModel =
            create(extras.createSavedStateHandle())

        fun create(@Assisted savedStateHandle: SavedStateHandle): StatsViewModel
    }

    companion object {
        const val STATS_TYPE_KEY = "com.albumsgenerator.app.presentation.screens.stats.STATS_TYPE"
    }
}
