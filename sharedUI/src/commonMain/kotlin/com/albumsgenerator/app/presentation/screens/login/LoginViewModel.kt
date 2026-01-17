package com.albumsgenerator.app.presentation.screens.login

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.login_success
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import androidx.navigation3.runtime.NavKey
import com.albumsgenerator.app.datasources.network.dtos.ProjectError
import com.albumsgenerator.app.datasources.repository.PreferencesRepository
import com.albumsgenerator.app.datasources.repository.ProjectRepository
import com.albumsgenerator.app.datasources.repository.StatsRepository
import com.albumsgenerator.app.di.modules.IO
import com.albumsgenerator.app.di.modules.Main
import com.albumsgenerator.app.presentation.navigation.Route
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metrox.viewmodel.ViewModelAssistedFactory
import dev.zacsweers.metrox.viewmodel.ViewModelAssistedFactoryKey
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.getString

@AssistedInject
class LoginViewModel(
    @Assisted savedStateHandle: SavedStateHandle,
    @param:IO private val ioDispatcher: CoroutineDispatcher,
    @param:Main private val mainDispatcher: CoroutineDispatcher,
    private val projectRepository: ProjectRepository,
    private val preferencesRepository: PreferencesRepository,
    private val statsRepository: StatsRepository,
) : ViewModel() {
    @OptIn(SavedStateHandleSaveableApi::class)
    var name by savedStateHandle.saveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }
        private set

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    fun update(newName: TextFieldValue) {
        name = newName
    }

    fun fetchProject() {
        _state.update { it.copy(isSubmitting = true) }
        viewModelScope.launch(ioDispatcher) {
            projectRepository.fetchAndStoreProject(name.text).fold(
                onSuccess = { project ->
                    preferencesRepository.updateProjectName(project.name)
                    statsRepository.fetchAndStoreStats()

                    withContext(mainDispatcher) {
                        _state.update { oldValue ->
                            oldValue.copy(
                                message = getString(Res.string.login_success),
                                nextRoute = if (project.history.lastOrNull()?.hasRating == true) {
                                    Route.CurrentAlbum
                                } else {
                                    Route.RateAlbum(project.name)
                                },
                            )
                        }
                    }
                },
                onFailure = { exception ->
                    if (exception is ClientRequestException) {
                        val error = exception.response.body<ProjectError>()
                        withContext(mainDispatcher) {
                            _state.update {
                                State(
                                    isSubmitting = false,
                                    message = error.message,
                                )
                            }
                        }
                    }
                },
            )
        }
    }

    data class State(
        val isSubmitting: Boolean = false,
        val message: String? = null,
        val nextRoute: NavKey? = null,
    )

    @AssistedFactory
    @ViewModelAssistedFactoryKey(LoginViewModel::class)
    @ContributesIntoMap(AppScope::class)
    @Suppress("unused")
    interface Factory : ViewModelAssistedFactory {
        override fun create(extras: CreationExtras): ViewModel =
            create(extras.createSavedStateHandle())

        fun create(@Assisted savedStateHandle: SavedStateHandle): LoginViewModel
    }
}
