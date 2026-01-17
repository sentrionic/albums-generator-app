package com.albumsgenerator.app.presentation.screens.current

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.current_album_missing_project
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.albumsgenerator.app.datasources.repository.HistoryRepository
import com.albumsgenerator.app.datasources.repository.PreferencesRepository
import com.albumsgenerator.app.datasources.repository.ProjectRepository
import com.albumsgenerator.app.domain.core.Coroutines
import com.albumsgenerator.app.domain.core.DataState
import com.albumsgenerator.app.domain.core.StreamingServices
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn

@ContributesIntoMap(AppScope::class)
@ViewModelKey(CurrentAlbumViewModel::class)
@Inject
class CurrentAlbumViewModel(
    projectRepository: ProjectRepository,
    preferencesRepository: PreferencesRepository,
    historyRepository: HistoryRepository,
) : ViewModel() {
    private val userData = preferencesRepository.userData

    @OptIn(ExperimentalCoroutinesApi::class)
    val state = userData
        .mapNotNull { it }
        .mapNotNull { it.projectName }
        .distinctUntilChanged() // Prevent project from being refetched if we just logged in.
        .flatMapConcat { projectName ->
            projectRepository.projectFlow(projectName).map { project ->
                if (project == null) {
                    DataState.Error(Res.string.current_album_missing_project)
                } else {
                    val previousAlbums =
                        historyRepository.artistHistories(project.currentAlbum.artist)

                    DataState.Success(
                        CurrentAlbumState(
                            project = project,
                            previousAlbums = previousAlbums,
                        ),
                    )
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(Coroutines.SUBSCRIPTION_TIMEOUT_MS),
            initialValue = DataState.Loading(),
        )

    val streamingService = userData
        .map { it.service ?: StreamingServices.SPOTIFY }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(Coroutines.SUBSCRIPTION_TIMEOUT_MS),
            initialValue = StreamingServices.SPOTIFY,
        )
}
