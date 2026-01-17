package com.albumsgenerator.app.datasources.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import java.io.File

fun createDataStore(): DataStore<Preferences> = createDataStore(
    producePath = {
        val file = File(System.getProperty("java.io.tmpdir"), DATA_STORE_FILE_NAME)
        file.absolutePath
    },
)
