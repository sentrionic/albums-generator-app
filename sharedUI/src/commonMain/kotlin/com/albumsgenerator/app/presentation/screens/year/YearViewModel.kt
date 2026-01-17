package com.albumsgenerator.app.presentation.screens.year

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.albumsgenerator.app.datasources.repository.HistoryRepository
import com.albumsgenerator.app.di.modules.IO
import com.albumsgenerator.app.domain.core.DataState
import com.albumsgenerator.app.domain.models.History
import com.albumsgenerator.app.presentation.navigation.Route
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metrox.viewmodel.ManualViewModelAssistedFactory
import dev.zacsweers.metrox.viewmodel.ManualViewModelAssistedFactoryKey
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@AssistedInject
class YearViewModel(
    @Assisted navKey: Route.Year,
    @param:IO private val ioDispatcher: CoroutineDispatcher,
    historyRepository: HistoryRepository,
) : ViewModel() {
    private val _histories: MutableStateFlow<DataState<List<History>>> =
        MutableStateFlow(DataState.Loading())
    val histories = _histories.asStateFlow()

    init {
        viewModelScope.launch(ioDispatcher) {
            _histories.update {
                DataState.Success(historyRepository.historiesByYear(navKey.year, 10))
            }
        }
    }

    @AssistedFactory
    @ManualViewModelAssistedFactoryKey(Factory::class)
    @ContributesIntoMap(AppScope::class)
    interface Factory : ManualViewModelAssistedFactory {
        fun create(@Assisted navKey: Route.Year): YearViewModel
    }
}
