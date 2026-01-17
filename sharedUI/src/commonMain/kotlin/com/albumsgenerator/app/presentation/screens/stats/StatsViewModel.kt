package com.albumsgenerator.app.presentation.screens.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.albumsgenerator.app.datasources.repository.StatsRepository
import com.albumsgenerator.app.di.modules.IO
import com.albumsgenerator.app.domain.core.Constants
import com.albumsgenerator.app.domain.core.Coroutines
import com.albumsgenerator.app.domain.core.DataState
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@ContributesIntoMap(AppScope::class)
@ViewModelKey(StatsViewModel::class)
@Inject
class StatsViewModel(
    @param:IO private val ioDispatcher: CoroutineDispatcher,
    private val statsRepository: StatsRepository,
) : ViewModel() {
    val state = statsRepository
        .statsFlow()
        .map { stats ->
            if (stats.isEmpty()) {
                return@map DataState.Loading()
            }

            val totalVotes = stats.sumOf { it.votes }
            val summedVotes = stats.sumOf { it.summedVotes }
            val statsSortedByControversialScore = stats.sortedByDescending {
                it.controversialScore
            }

            DataState.Success(
                StatsScreenState(
                    topAlbums = stats.take(Constants.LIMIT),
                    bottomAlbums = stats.takeLast(Constants.LIMIT).reversed(),
                    mostControversial = statsSortedByControversialScore.take(Constants.LIMIT),
                    leastControversial = statsSortedByControversialScore.takeLast(Constants.LIMIT)
                        .reversed(),
                    votes = totalVotes - summedVotes,
                    averageRating =
                        stats.sumOf { it.votesByGrade.average }.toFloat() / summedVotes.toFloat(),
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
