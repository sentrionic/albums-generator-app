package com.albumsgenerator.app.datasources.network.dtos

import com.albumsgenerator.app.domain.models.VotesByGrade
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VotesByGradeDto(
    @SerialName("1")
    val x1: Int,
    @SerialName("2")
    val x2: Int,
    @SerialName("3")
    val x3: Int,
    @SerialName("4")
    val x4: Int,
    @SerialName("5")
    val x5: Int,
) {
    fun toDomain(): VotesByGrade = VotesByGrade(
        x1 = x1,
        x2 = x2,
        x3 = x3,
        x4 = x4,
        x5 = x5,
    )

    companion object {
        fun fromDomain(votes: VotesByGrade): VotesByGradeDto = VotesByGradeDto(
            x1 = votes.x1,
            x2 = votes.x2,
            x3 = votes.x3,
            x4 = votes.x4,
            x5 = votes.x5,
        )
    }
}
