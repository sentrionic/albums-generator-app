package com.albumsgenerator.app.datasources.network.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProjectError(
    @SerialName("error")
    val error: Boolean,
    @SerialName("errorCode")
    val errorCode: String,
    @SerialName("message")
    val message: String,
)
