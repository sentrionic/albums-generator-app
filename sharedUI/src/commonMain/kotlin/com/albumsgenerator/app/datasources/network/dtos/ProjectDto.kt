package com.albumsgenerator.app.datasources.network.dtos

import com.albumsgenerator.app.domain.models.Project
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProjectDto(
    @SerialName("currentAlbum")
    val currentAlbum: AlbumDto,
    @SerialName("currentAlbumNotes")
    val currentAlbumNotes: String,
    @SerialName("history")
    val history: List<HistoryDto>,
    @SerialName("name")
    val name: String,
    @SerialName("paused")
    val paused: Boolean,
    @SerialName("shareableUrl")
    val shareableUrl: String,
    @SerialName("updateFrequency")
    val updateFrequency: String,
) {
    fun toDomain(): Project = Project(
        currentAlbum = currentAlbum.toDomain(),
        currentAlbumNotes = currentAlbumNotes,
        history = history.mapIndexed { index, dto -> dto.toDomain(index) },
        name = name,
        paused = paused,
        shareableUrl = shareableUrl,
        updateFrequency = updateFrequency,
    )
}
