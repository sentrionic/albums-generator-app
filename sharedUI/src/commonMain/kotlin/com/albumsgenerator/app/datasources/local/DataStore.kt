package com.albumsgenerator.app.datasources.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import okio.Path.Companion.toPath

fun createDataStore(producePath: () -> String): DataStore<Preferences> =
    PreferenceDataStoreFactory.createWithPath(
        produceFile = { producePath().toPath() },
    )

internal const val DATA_STORE_FILE_NAME = "albums-generator.preferences_pb"

object DataStoreKeys {
    val PROJECT_NAME = stringPreferencesKey("project_name")
    val SELECTED_STREAMING_SERVICE = intPreferencesKey("selected_streaming_service")
    val SELECTED_THEME_MODE = intPreferencesKey("selected_theme_mode")
}
