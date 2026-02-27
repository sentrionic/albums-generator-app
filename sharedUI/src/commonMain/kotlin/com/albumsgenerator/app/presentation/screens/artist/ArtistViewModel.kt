package com.albumsgenerator.app.presentation.screens.artist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.albumsgenerator.app.datasources.repository.HistoryRepository
import com.albumsgenerator.app.datasources.repository.PreferencesRepository
import com.albumsgenerator.app.datasources.repository.StatsRepository
import com.albumsgenerator.app.domain.core.Coroutines
import com.albumsgenerator.app.domain.core.DataState
import com.albumsgenerator.app.presentation.navigation.Route
import com.albumsgenerator.app.presentation.screens.top.TopState
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metrox.viewmodel.ManualViewModelAssistedFactory
import dev.zacsweers.metrox.viewmodel.ManualViewModelAssistedFactoryKey
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

@AssistedInject
class ArtistViewModel(
    @Assisted navKey: Route.Artist,
    statsRepository: StatsRepository,
    historyRepository: HistoryRepository,
    preferencesRepository: PreferencesRepository,
) : ViewModel() {
    val state = combine(
        flow { emit(historyRepository.artistHistories(navKey.artist)) },
        statsRepository.statsForArtist(navKey.artist),
        preferencesRepository.userData,
    ) { histories, stats, userData ->
        DataState.Success(
            TopState(
                histories = histories.sortedBy { it.album.releaseDate },
                stats = stats.sortedBy { it.releaseDate },
                spoilerMode = userData.spoilerMode,
            ),
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(Coroutines.SUBSCRIPTION_TIMEOUT_MS),
            initialValue = DataState.Loading(),
        )

    @AssistedFactory
    @ManualViewModelAssistedFactoryKey(Factory::class)
    @ContributesIntoMap(AppScope::class)
    interface Factory : ManualViewModelAssistedFactory {
        fun create(@Assisted navKey: Route.Artist): ArtistViewModel
    }
}
