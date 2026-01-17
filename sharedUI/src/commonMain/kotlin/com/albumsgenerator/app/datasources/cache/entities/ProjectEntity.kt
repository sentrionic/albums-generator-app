package com.albumsgenerator.app.datasources.cache.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.albumsgenerator.app.domain.models.Album
import com.albumsgenerator.app.domain.models.History
import com.albumsgenerator.app.domain.models.Project

@Entity(tableName = "projects")
data class ProjectEntity(
    @ColumnInfo("current_album_id")
    val currentAlbumId: String,
    @ColumnInfo("current_album_notes")
    val currentAlbumNotes: String,
    @PrimaryKey
    val name: String,
    @ColumnInfo("paused")
    val paused: Boolean,
    @ColumnInfo("shareable_url")
    val shareableUrl: String,
    @ColumnInfo("update_frequency")
    val updateFrequency: String,
) {
    fun toDomain(
        currentAlbum: Album,
        history: List<History>,
    ): Project = Project(
        currentAlbum = currentAlbum,
        currentAlbumNotes = currentAlbumNotes,
        history = history,
        name = name,
        paused = paused,
        shareableUrl = shareableUrl,
        updateFrequency = updateFrequency,
    )

    companion object {
        fun fromDomain(project: Project): ProjectEntity = ProjectEntity(
            currentAlbumId = project.currentAlbum.uuid,
            currentAlbumNotes = project.currentAlbumNotes,
            name = project.name,
            paused = project.paused,
            shareableUrl = project.shareableUrl,
            updateFrequency = project.updateFrequency,
        )
    }
}
