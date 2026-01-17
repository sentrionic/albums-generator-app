package com.albumsgenerator.app.datasources.cache.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.albumsgenerator.app.datasources.cache.entities.ProjectEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProjectDao {
    @Query("SELECT * FROM projects where name = :name")
    fun getProject(name: String): Flow<ProjectEntity?>

    @Upsert
    suspend fun insert(project: ProjectEntity)
}
