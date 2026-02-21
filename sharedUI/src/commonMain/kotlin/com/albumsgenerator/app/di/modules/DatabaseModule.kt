package com.albumsgenerator.app.di.modules

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.albumsgenerator.app.datasources.cache.AppDatabase
import com.albumsgenerator.app.datasources.cache.daos.AlbumDao
import com.albumsgenerator.app.datasources.cache.daos.HistoryDao
import com.albumsgenerator.app.datasources.cache.daos.ProjectDao
import com.albumsgenerator.app.datasources.cache.daos.StatsDao
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn

@BindingContainer
@Suppress("unused")
object DatabaseModule {
    @Provides
    @SingleIn(AppScope::class)
    fun providesAppDatabase(builder: RoomDatabase.Builder<AppDatabase>): AppDatabase = builder
        .setDriver(BundledSQLiteDriver())
        .fallbackToDestructiveMigration(true)
        .build()

    @Provides
    fun providesAlbumDao(db: AppDatabase): AlbumDao = db.albumDao()

    @Provides
    fun providesProjectDao(db: AppDatabase): ProjectDao = db.projectDao()

    @Provides
    fun providesHistoryDao(db: AppDatabase): HistoryDao = db.historyDao()

    @Provides
    fun providesStatsDao(db: AppDatabase): StatsDao = db.statsDao()
}
