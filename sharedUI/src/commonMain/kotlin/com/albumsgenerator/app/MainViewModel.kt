package com.albumsgenerator.app

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation3.runtime.NavKey
import com.albumsgenerator.app.datasources.repository.HistoryRepository
import com.albumsgenerator.app.datasources.repository.PreferencesRepository
import com.albumsgenerator.app.datasources.repository.ProjectRepository
import com.albumsgenerator.app.di.modules.IO
import com.albumsgenerator.app.di.modules.Main
import com.albumsgenerator.app.domain.core.Coroutines
import com.albumsgenerator.app.presentation.navigation.Route
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@ContributesIntoMap(AppScope::class)
@ViewModelKey(MainViewModel::class)
@Inject
class MainViewModel(
    @param:IO private val ioDispatcher: CoroutineDispatcher,
    @param:Main private val mainDispatcher: CoroutineDispatcher,
    preferencesRepository: PreferencesRepository,
    private val historyRepository: HistoryRepository,
    private val projectRepository: ProjectRepository,
) : ViewModel() {
    val backStack = mutableStateListOf<NavKey>(Route.Splash)

    val userData = preferencesRepository
        .userData
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(Coroutines.SUBSCRIPTION_TIMEOUT_MS),
            initialValue = null,
        )

    init {
        viewModelScope.launch(ioDispatcher) {
            userData
                .mapNotNull { it }
                .map { it.projectName }
                .distinctUntilChanged { old, new ->
                    if (old == null && new != null) {
                        true
                    } else {
                        old == new
                    }
                }
                .collect { name ->
                    withContext(mainDispatcher) {
                        if (name.isNullOrBlank()) {
                            replace(Route.Login)
                        } else {
                            fetchProject(name)
                        }
                    }
                }
        }
    }

    private fun fetchProject(name: String) {
        viewModelScope.launch(ioDispatcher) {
            projectRepository.fetchAndStoreProject(name)
            val hasUnratedAlbum = historyRepository.hasUnratedHistory()
            withContext(mainDispatcher) {
                if (hasUnratedAlbum) {
                    replace(Route.RateAlbum(name))
                } else {
                    replace(Route.CurrentAlbum)
                }
            }
        }
    }

    fun replace(route: NavKey) {
        backStack.clear()
        backStack.add(route)
    }
}
