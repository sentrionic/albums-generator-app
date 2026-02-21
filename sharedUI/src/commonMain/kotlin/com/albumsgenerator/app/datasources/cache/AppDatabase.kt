package com.albumsgenerator.app.datasources.cache

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.Transactor
import androidx.room.execSQL
import com.albumsgenerator.app.datasources.cache.daos.AlbumDao
import com.albumsgenerator.app.datasources.cache.daos.HistoryDao
import com.albumsgenerator.app.datasources.cache.daos.ProjectDao
import com.albumsgenerator.app.datasources.cache.daos.StatsDao
import com.albumsgenerator.app.datasources.cache.entities.AlbumEntity
import com.albumsgenerator.app.datasources.cache.entities.HistoryEntity
import com.albumsgenerator.app.datasources.cache.entities.ProjectEntity
import com.albumsgenerator.app.datasources.cache.entities.StatEntity

@Database(
    entities = [
        AlbumEntity::class,
        HistoryEntity::class,
        ProjectEntity::class,
        StatEntity::class,
    ],
    version = 2,
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun albumDao(): AlbumDao
    abstract fun historyDao(): HistoryDao
    abstract fun projectDao(): ProjectDao
    abstract fun statsDao(): StatsDao
}

suspend fun AppDatabase.clearDB() {
    this.useConnection(isReadOnly = false) { connection ->
        val tableNames = listOf("albums", "histories", "projects", "stats")
        connection.withTransaction(Transactor.SQLiteTransactionType.IMMEDIATE) {
            tableNames.forEach { tableName -> execSQL("DELETE FROM `$tableName`") }
        }
        if (!connection.inTransaction()) {
            connection.execSQL("PRAGMA wal_checkpoint(FULL)")
            connection.execSQL("VACUUM")
            invalidationTracker.refreshAsync()
        }
    }
}

@Suppress("KotlinNoActualForExpect", "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}

internal const val DATABASE_NAME = "album-generator-database"
