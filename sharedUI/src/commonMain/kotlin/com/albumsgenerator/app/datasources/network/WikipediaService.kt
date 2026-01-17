package com.albumsgenerator.app.datasources.network

import com.albumsgenerator.app.datasources.network.dtos.PageContent
import com.albumsgenerator.app.datasources.network.dtos.WikipediaDto
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

interface WikipediaService {
    suspend fun getSummary(title: String): String?
}

@ContributesBinding(AppScope::class)
@Inject
@Suppress("Unused")
class RealWikipediaService(private val httpClient: HttpClient) : WikipediaService {
    override suspend fun getSummary(title: String) = httpClient.get(
        "https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro&explaintext&redirects=1&titles=$title",
    )
        .body<WikipediaDto>()
        .query.pages.values.firstOrNull()?.let {
            Json.decodeFromJsonElement<PageContent>(it)
        }
        ?.extract
}
