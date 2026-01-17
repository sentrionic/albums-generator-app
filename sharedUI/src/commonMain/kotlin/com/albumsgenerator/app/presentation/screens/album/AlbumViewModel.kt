package com.albumsgenerator.app.presentation.screens.album

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.album_error_missing_history
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.albumsgenerator.app.datasources.repository.AlbumRepository
import com.albumsgenerator.app.datasources.repository.HistoryRepository
import com.albumsgenerator.app.datasources.repository.StatsRepository
import com.albumsgenerator.app.di.modules.IO
import com.albumsgenerator.app.domain.core.Coroutines
import com.albumsgenerator.app.domain.core.DataState
import com.albumsgenerator.app.presentation.navigation.Route
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metrox.viewmodel.ManualViewModelAssistedFactory
import dev.zacsweers.metrox.viewmodel.ManualViewModelAssistedFactoryKey
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@AssistedInject
class AlbumViewModel(
    @Assisted navKey: Route.Album,
    @param:IO private val ioDispatcher: CoroutineDispatcher,
    statsRepository: StatsRepository,
    historyRepository: HistoryRepository,
    private val albumRepository: AlbumRepository,
) : ViewModel() {
    val history = if (navKey.albumName.isNotEmpty() &&
        !navKey.albumArtist.isNullOrEmpty()
    ) {
        historyRepository.historyFlow(navKey.albumName, navKey.albumArtist)
    } else {
        historyRepository.historyFlow(navKey.albumId)
    }
        .map { album ->
            if (album == null) {
                DataState.Error(Res.string.album_error_missing_history)
            } else {
                DataState.Success(album)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(Coroutines.SUBSCRIPTION_TIMEOUT_MS),
            initialValue = DataState.Loading(),
        )

    val stats = statsRepository
        .statsForAlbum(navKey.albumName)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(Coroutines.SUBSCRIPTION_TIMEOUT_MS),
            initialValue = null,
        )

    init {
        viewModelScope.launch(ioDispatcher) {
            if (navKey.albumName.isNotEmpty() && !navKey.albumArtist.isNullOrEmpty()) {
                albumRepository.getAndStoreAlbum(navKey.albumName, navKey.albumArtist)
            } else {
                albumRepository.getAndStoreAlbum(navKey.albumId)
            }
        }
    }

    @AssistedFactory
    @ManualViewModelAssistedFactoryKey(Factory::class)
    @ContributesIntoMap(AppScope::class)
    interface Factory : ManualViewModelAssistedFactory {
        fun create(@Assisted navKey: Route.Album): AlbumViewModel
    }
}
