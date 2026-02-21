package com.albumsgenerator.app.datasources.network

import com.albumsgenerator.app.BuildConfig
import com.albumsgenerator.app.datasources.network.dtos.ProjectDto
import com.albumsgenerator.app.datasources.network.dtos.Stats
import com.albumsgenerator.app.domain.models.AlbumStats
import com.albumsgenerator.app.domain.models.Project
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

interface AlbumGeneratorService {
    suspend fun getProject(name: String): Project
    suspend fun getStats(): List<AlbumStats>
    suspend fun getUserAlbumStats(): List<AlbumStats>
}

@ContributesBinding(AppScope::class)
@Suppress("Unused")
class RealAlbumGeneratorService(private val httpClient: HttpClient) : AlbumGeneratorService {
    override suspend fun getProject(name: String) = httpClient
        .get("${BuildConfig.WEBSITE_API}/projects/$name")
        .body<ProjectDto>()
        .toDomain()

    override suspend fun getStats() = httpClient
        .get("${BuildConfig.WEBSITE_API}/albums/stats")
        .body<Stats>()
        .albums.map { it.toDomain() }

    override suspend fun getUserAlbumStats() = httpClient
        .get("${BuildConfig.WEBSITE_API}/user-albums/stats")
        .body<Stats>()
        .albums.map { it.toDomain() }
}
