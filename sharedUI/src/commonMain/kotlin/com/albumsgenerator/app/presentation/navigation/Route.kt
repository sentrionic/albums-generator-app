package com.albumsgenerator.app.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route : NavKey {
    @Serializable
    data object Splash : Route, NavKey

    @Serializable
    data object Login : Route, NavKey

    @Serializable
    data class RateAlbum(val name: String) :
        Route,
        NavKey

    @Serializable
    data object CurrentAlbum : Route, NavKey

    @Serializable
    data object History : Route, NavKey

    @Serializable
    data object Stats : Route, NavKey

    @Serializable
    data object Summary : Route, NavKey

    @Serializable
    data object Settings : Route, NavKey

    @Serializable
    data class Album(val albumId: String, val albumName: String, val albumArtist: String? = null) :
        Route,
        NavKey

    @Serializable
    data class Artist(val artist: String) :
        Route,
        NavKey

    @Serializable
    data class Genre(val genre: String) :
        Route,
        NavKey

    @Serializable
    data class Year(val year: String) :
        Route,
        NavKey

    @Serializable
    data class Web(val url: String, val title: String) :
        Route,
        NavKey
}
