package com.albumsgenerator.app.datasources.network.dtos

import com.albumsgenerator.app.domain.models.Image
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageDto(
    @SerialName("height")
    val height: Int,
    @SerialName("url")
    val url: String,
    @SerialName("width")
    val width: Int,
) {
    fun toDomain(): Image = Image(
        height = height,
        url = url,
        width = width,
    )
}
