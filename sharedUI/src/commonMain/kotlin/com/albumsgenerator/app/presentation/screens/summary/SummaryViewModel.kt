package com.albumsgenerator.app.presentation.screens.summary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.albumsgenerator.app.BuildConfig
import com.albumsgenerator.app.datasources.repository.HistoryRepository
import com.albumsgenerator.app.di.modules.IO
import com.albumsgenerator.app.di.modules.Main
import com.albumsgenerator.app.domain.core.DataState
import com.albumsgenerator.app.domain.values.Rating
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@ContributesIntoMap(AppScope::class)
@ViewModelKey(SummaryViewModel::class)
@Inject
class SummaryViewModel(
    @param:IO private val ioDispatcher: CoroutineDispatcher,
    @param:Main private val mainDispatcher: CoroutineDispatcher,
    private val historyRepository: HistoryRepository,
) : ViewModel() {
    private val _summary: MutableStateFlow<DataState<SummaryScreenState>> =
        MutableStateFlow(DataState.Loading())
    val summary = _summary.asStateFlow()

    init {
        viewModelScope.launch(ioDispatcher) {
            val historyCount = async { historyRepository.historyCount() }
            val ratedAlbums = async { historyRepository.ratedAlbumsCount() }
            val averageRating = async { historyRepository.averageRating() }
            val fiveStarAlbums = async { historyRepository.historiesByRating(Rating("5")) }
            val oneStarAlbums = async { historyRepository.historiesByRating(Rating("1")) }

            withContext(mainDispatcher) {
                _summary.update {
                    DataState.Success(
                        SummaryScreenState(
                            albumsRated = ratedAlbums.await(),
                            averageRating = averageRating.await(),
                            percentageComplete =
                                historyCount.await().toFloat() /
                                    BuildConfig.TOTAL_ALBUMS_COUNT.toFloat(),
                            fiveStarAlbums = fiveStarAlbums.await().map { it.album },
                            oneStarAlbums = oneStarAlbums.await().map { it.album },
                        ),
                    )
                }
            }
        }
    }
}
