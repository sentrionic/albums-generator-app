package com.albumsgenerator.app.datasources.network

import co.touchlab.kermit.Logger
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger as LoggerPlugin
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun <T : HttpClientEngineConfig> HttpClientConfig<T>.configure() {
    expectSuccess = true
    install(ContentNegotiation) {
        json(
            Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            },
        )
    }
    install(Logging) {
        logger = object : LoggerPlugin {
            override fun log(message: String) {
                Logger.withTag("HttpClient").i(message)
            }
        }
        level = LogLevel.INFO
    }
}
