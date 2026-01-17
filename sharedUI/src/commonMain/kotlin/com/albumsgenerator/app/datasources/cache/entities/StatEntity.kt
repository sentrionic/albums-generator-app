package com.albumsgenerator.app.datasources.cache.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.albumsgenerator.app.datasources.network.dtos.AlbumStatsDto
import com.albumsgenerator.app.domain.models.AlbumStats
import kotlinx.serialization.json.Json

@Entity(tableName = "stats")
data class StatEntity(
    @ColumnInfo("artist")
    val artist: String,
    @ColumnInfo("average_rating")
    val averageRating: Double,
    @ColumnInfo("controversial_score")
    val controversialScore: Double,
    @ColumnInfo("genres")
    val genres: String,
    @PrimaryKey
    val name: String,
    @ColumnInfo("votes")
    val votes: Int,
    @ColumnInfo("votes_by_grade")
    val votesByGrade: String,
) {
    fun toDomain(): AlbumStats = AlbumStats(
        artist = artist,
        averageRating = averageRating,
        controversialScore = controversialScore,
        genres = genres.split(SEPARATOR),
        name = name,
        votes = votes,
        votesByGrade = Json.decodeFromString<AlbumStatsDto.VotesByGradeDto>(votesByGrade)
            .toDomain(),
    )

    companion object {
        const val SEPARATOR = ","

        fun fromDomain(stat: AlbumStats): StatEntity = StatEntity(
            artist = stat.artist,
            averageRating = stat.averageRating,
            controversialScore = stat.controversialScore,
            genres = stat.genres.joinToString(SEPARATOR),
            name = stat.name,
            votes = stat.votes,
            votesByGrade = Json.encodeToString(
                AlbumStatsDto.VotesByGradeDto.fromDomain(stat.votesByGrade),
            ),
        )
    }
}
