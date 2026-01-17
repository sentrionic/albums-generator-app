package com.albumsgenerator.app.domain.core

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.ic_amazon_music
import albumsgenerator.sharedui.generated.resources.ic_apple_music
import albumsgenerator.sharedui.generated.resources.ic_deezer
import albumsgenerator.sharedui.generated.resources.ic_qobuz
import albumsgenerator.sharedui.generated.resources.ic_spotify
import albumsgenerator.sharedui.generated.resources.ic_tidal
import albumsgenerator.sharedui.generated.resources.ic_youtube
import org.jetbrains.compose.resources.DrawableResource

enum class StreamingServices(val label: String, val url: String, val logo: DrawableResource) {
    AMAZON(
        label = "Amazon Music",
        url = "https://music.amazon.com/albums/",
        logo = Res.drawable.ic_amazon_music,
    ),
    APPLE(
        label = "Apple Music",
        url = "https://music.apple.com/album/",
        logo = Res.drawable.ic_apple_music,
    ),
    DEEZER(
        label = "Deezer",
        url = "https://deezer.com/album/",
        logo = Res.drawable.ic_deezer,
    ),
    QOBUZ(
        label = "Qobuz",
        url = "https://play.qobuz.com/album/",
        logo = Res.drawable.ic_qobuz,
    ),
    SPOTIFY(
        label = "Spotify",
        url = "spotify:album:",
        logo = Res.drawable.ic_spotify,
    ),
    TIDAL(
        label = "Tidal",
        url = "https://tidal.com/browse/album/",
        logo = Res.drawable.ic_tidal,
    ),
    YOUTUBE(
        label = "Youtube",
        url = "https://music.youtube.com/playlist?list=",
        logo = Res.drawable.ic_youtube,
    ),
}
