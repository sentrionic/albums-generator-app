package com.albumsgenerator.app.presentation.screens.settings

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.settings_refresh_active
import albumsgenerator.sharedui.generated.resources.settings_refresh_success
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.albumsgenerator.app.datasources.cache.AppDatabase
import com.albumsgenerator.app.datasources.cache.clearDB
import com.albumsgenerator.app.datasources.repository.PreferencesRepository
import com.albumsgenerator.app.datasources.repository.ProjectRepository
import com.albumsgenerator.app.datasources.repository.StatsRepository
import com.albumsgenerator.app.di.modules.IO
import com.albumsgenerator.app.di.modules.Main
import com.albumsgenerator.app.domain.core.Coroutines
import com.albumsgenerator.app.domain.core.StreamingServices
import com.albumsgenerator.app.domain.core.Theme
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.StringResource

@ContributesIntoMap(AppScope::class)
@ViewModelKey(SettingsViewModel::class)
@Inject
class SettingsViewModel(
    @param:IO private val ioDispatcher: CoroutineDispatcher,
    @param:Main private val mainDispatcher: CoroutineDispatcher,
    private val projectRepository: ProjectRepository,
    private val statsRepository: StatsRepository,
    private val preferencesRepository: PreferencesRepository,
    private val db: AppDatabase,
) : ViewModel() {
    val userData = preferencesRepository
        .userData
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(Coroutines.SUBSCRIPTION_TIMEOUT_MS),
            initialValue = null,
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    val project = userData
        .mapNotNull { it?.projectName }
        .distinctUntilChanged()
        .flatMapConcat {
            projectRepository.projectFlow(it)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(Coroutines.SUBSCRIPTION_TIMEOUT_MS),
            initialValue = null,
        )

    var message by mutableStateOf<StringResource?>(null)
        private set

    var shouldLogout by mutableStateOf(false)
        private set

    fun onEvent(event: SettingsEvents) {
        when (event) {
            SettingsEvents.Logout -> logout()
            is SettingsEvents.UpdateStreamingService -> updateStreamingService(event.service)
            is SettingsEvents.UpdateTheme -> updateTheme(event.theme)
            is SettingsEvents.Refresh -> reload()
            is SettingsEvents.ResetMessage -> message = null
        }
    }

    private fun updateStreamingService(service: StreamingServices) {
        viewModelScope.launch(ioDispatcher) {
            preferencesRepository.updateStreamingService(service)
        }
    }

    private fun updateTheme(theme: Theme) {
        viewModelScope.launch(ioDispatcher) {
            preferencesRepository.updateTheme(theme)
        }
    }

    private fun reload() {
        message = Res.string.settings_refresh_active
        val name = project.value?.name ?: return
        viewModelScope.launch(ioDispatcher) {
            val projectResult = async { projectRepository.fetchAndStoreProject(name) }
            val statsResult = async { statsRepository.fetchAndStoreStats() }

            projectResult.await()
            statsResult.await()
            withContext(mainDispatcher) {
                message = Res.string.settings_refresh_success
            }
        }
    }

    private fun logout() {
        viewModelScope.launch(ioDispatcher) {
            db.clearDB()
            preferencesRepository.clear()
            withContext(mainDispatcher) {
                shouldLogout = true
            }
        }
    }
}
