package com.albumsgenerator.app.di

import android.app.Activity
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.RoomDatabase
import com.albumsgenerator.app.datasources.cache.AppDatabase
import com.albumsgenerator.app.datasources.cache.getDatabaseBuilder
import com.albumsgenerator.app.datasources.local.createDataStore
import com.albumsgenerator.app.datasources.network.configure
import com.albumsgenerator.app.di.modules.DatabaseModule
import com.albumsgenerator.app.di.modules.DispatchersModule
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp

@DependencyGraph(
    scope = AppScope::class,
    bindingContainers = [
        DatabaseModule::class,
        DispatchersModule::class,
    ],
)
@Suppress("unused")
interface AndroidAppGraph : AppGraph {
    @Provides
    fun provideApplicationContext(activity: Activity): Context = activity

    @Provides
    fun provideHttpClient(): HttpClient = HttpClient(OkHttp) { configure() }

    @Provides
    @SingleIn(AppScope::class)
    fun providesDatabaseBuilder(context: Context): RoomDatabase.Builder<AppDatabase> =
        getDatabaseBuilder(context)

    @Provides
    @SingleIn(AppScope::class)
    fun providesDataStore(context: Context): DataStore<Preferences> = createDataStore(context)

    @DependencyGraph.Factory
    fun interface Factory {
        fun create(@Provides activity: Activity): AndroidAppGraph
    }
}
