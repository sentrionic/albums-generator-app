package com.albumsgenerator.app.datasources.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.albumsgenerator.app.datasources.local.DataStoreKeys
import com.albumsgenerator.app.domain.core.StreamingServices
import com.albumsgenerator.app.domain.core.Theme
import com.albumsgenerator.app.domain.models.SpoilerMode
import com.albumsgenerator.app.domain.models.UserData
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface PreferencesRepository {
    val userData: Flow<UserData>

    suspend fun updateProjectName(name: String)
    suspend fun updateTheme(theme: Theme)
    suspend fun updateStreamingService(service: StreamingServices)
    suspend fun updateSpoilerMode(mode: SpoilerMode)
    suspend fun clear()
}

@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
@Suppress("Unused")
class RealPreferencesRepository(private val dataStore: DataStore<Preferences>) :
    PreferencesRepository {
    override val userData = dataStore
        .data
        .map { prefs ->
            UserData(
                projectName = prefs[DataStoreKeys.PROJECT_NAME].takeIf { !it.isNullOrEmpty() },
                theme =
                    prefs[DataStoreKeys.SELECTED_THEME_MODE]?.let { Theme.entries.getOrNull(it) }
                        ?: Theme.SYSTEM,
                service = prefs[DataStoreKeys.SELECTED_STREAMING_SERVICE]?.let {
                    StreamingServices.entries.getOrNull(
                        it,
                    )
                },
                spoilerMode = prefs[DataStoreKeys.SELECTED_SPOILER_MODE]?.let { mode ->
                    SpoilerMode.entries.getOrNull(mode)
                } ?: SpoilerMode.VISIBLE,
            )
        }

    override suspend fun updateProjectName(name: String) {
        dataStore.edit {
            it[DataStoreKeys.PROJECT_NAME] = name
        }
    }

    override suspend fun updateTheme(theme: Theme) {
        dataStore.edit {
            it[DataStoreKeys.SELECTED_THEME_MODE] = theme.ordinal
        }
    }

    override suspend fun updateStreamingService(service: StreamingServices) {
        dataStore.edit {
            it[DataStoreKeys.SELECTED_STREAMING_SERVICE] = service.ordinal
        }
    }

    override suspend fun updateSpoilerMode(mode: SpoilerMode) {
        dataStore.edit {
            it[DataStoreKeys.SELECTED_SPOILER_MODE] = mode.ordinal
        }
    }

    override suspend fun clear() {
        dataStore.edit {
            it.remove(DataStoreKeys.PROJECT_NAME)
            it.remove(DataStoreKeys.SELECTED_THEME_MODE)
            it.remove(DataStoreKeys.SELECTED_STREAMING_SERVICE)
            it.remove(DataStoreKeys.SELECTED_SPOILER_MODE)
        }
    }
}
