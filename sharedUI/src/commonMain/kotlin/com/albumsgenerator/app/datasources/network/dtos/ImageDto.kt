package com.albumsgenerator.app.datasources.network.dtos

import com.albumsgenerator.app.domain.models.Image
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageDto(
    @SerialName("height")
    val height: Int? = null,
    @SerialName("url")
    val url: String,
    @SerialName("width")
    val width: Int? = null,
) {
    fun toDomain(): Image = Image(
        height = height ?: Image.INVALID_SIZE,
        url = url,
        width = width ?: Image.INVALID_SIZE,
    )
}
