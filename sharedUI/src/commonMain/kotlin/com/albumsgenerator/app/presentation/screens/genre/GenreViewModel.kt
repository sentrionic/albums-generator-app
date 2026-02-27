package com.albumsgenerator.app.presentation.screens.genre

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.albumsgenerator.app.datasources.repository.HistoryRepository
import com.albumsgenerator.app.datasources.repository.PreferencesRepository
import com.albumsgenerator.app.datasources.repository.StatsRepository
import com.albumsgenerator.app.domain.core.Constants
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
import kotlinx.coroutines.flow.stateIn

@AssistedInject
class GenreViewModel(
    @Assisted navKey: Route.Genre,
    statsRepository: StatsRepository,
    historyRepository: HistoryRepository,
    preferencesRepository: PreferencesRepository,
) : ViewModel() {
    val state = combine(
        historyRepository.genreHistories(genre = navKey.genre, limit = Constants.LIMIT),
        statsRepository.statsForGenre(genre = navKey.genre, limit = Constants.LIMIT),
        preferencesRepository.userData,
    ) { histories, stats, userData ->
        DataState.Success(
            TopState(
                histories = histories,
                stats = stats,
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
        fun create(@Assisted navKey: Route.Genre): GenreViewModel
    }
}
