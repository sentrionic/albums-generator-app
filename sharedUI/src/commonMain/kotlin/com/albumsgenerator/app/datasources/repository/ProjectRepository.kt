package com.albumsgenerator.app.datasources.repository

import co.touchlab.kermit.Logger
import com.albumsgenerator.app.datasources.cache.daos.AlbumDao
import com.albumsgenerator.app.datasources.cache.daos.HistoryDao
import com.albumsgenerator.app.datasources.cache.daos.ProjectDao
import com.albumsgenerator.app.datasources.cache.entities.AlbumEntity
import com.albumsgenerator.app.datasources.cache.entities.HistoryEntity
import com.albumsgenerator.app.datasources.cache.entities.ProjectEntity
import com.albumsgenerator.app.datasources.network.AlbumGeneratorService
import com.albumsgenerator.app.domain.models.Project
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface ProjectRepository {
    fun projectFlow(name: String): Flow<Project?>
    suspend fun fetchAndStoreProject(name: String): Result<Project>
}

@ContributesBinding(AppScope::class)
@Suppress("Unused")
class RealProjectRepository(
    val albumGeneratorService: AlbumGeneratorService,
    val projectDao: ProjectDao,
    val albumDao: AlbumDao,
    val historyDao: HistoryDao,
) : ProjectRepository {
    private val logger = Logger.withTag("RealProjectRepository")

    override fun projectFlow(name: String): Flow<Project?> {
        logger.i { "[ProjectFlow] Fetching the project for the current user" }
        return projectDao.getProject(name).map { value ->
            if (value == null) {
                logger.w { "[ProjectFlow] Could not fetch the project for the given name." }
                return@map null
            }
            val album = albumDao.getById(value.currentAlbumId)?.toDomain()
            if (album == null) {
                logger.w { "[ProjectFlow] Could not fetch the current album for the project." }
                return@map null
            }

            val project = value.toDomain(
                currentAlbum = album,
                history = emptyList(),
            )

            logger.d { "[ProjectFlow] Successfully fetched the project." }

            project
        }
    }

    override suspend fun fetchAndStoreProject(name: String): Result<Project> {
        logger.i { "[FetchAndStoreProject] Fetching the project." }
        return try {
            val project = albumGeneratorService.getProject(name)

            val currentAlbum = project.currentAlbum
            val albums = mutableListOf(AlbumEntity.fromDomain(currentAlbum))
            val histories = mutableListOf<HistoryEntity>()

            for ((index, history) in project.history.withIndex()) {
                albums += AlbumEntity.fromDomain(history.album)
                histories += HistoryEntity.fromDomain(history.copy(index = index))
            }

            coroutineScope { async { albumDao.insertAll(albums) } }
            coroutineScope { async { historyDao.insertAll(histories) } }

            projectDao.insert(ProjectEntity.fromDomain(project))
            logger.d { "Successfully fetched and stored the project" }
            Result.success(project)
        } catch (e: Exception) {
            logger.w(e) { "[FetchAndStoreProject] Something went wrong: $e" }
            Result.failure(e)
        }
    }
}
