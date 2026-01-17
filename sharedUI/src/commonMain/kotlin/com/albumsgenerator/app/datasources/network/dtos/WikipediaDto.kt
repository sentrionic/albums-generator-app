package com.albumsgenerator.app.datasources.network.dtos

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys
import kotlinx.serialization.json.JsonObject

@Serializable
data class WikipediaDto(
    @SerialName("query")
    val query: Query,
)

@Serializable
data class Query(
    @SerialName("pages")
    val pages: JsonObject,
)

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class PageContent(
    @SerialName("extract")
    val extract: String,
)
