package com.albumsgenerator.app.presentation.utils

import com.albumsgenerator.app.domain.core.LabelValuePair
import com.albumsgenerator.app.domain.models.Album
import com.albumsgenerator.app.domain.models.AlbumStats
import com.albumsgenerator.app.domain.models.History
import com.albumsgenerator.app.domain.models.Project
import kotlin.time.Instant

object PreviewData {
    @Suppress("ktlint:standard:max-line-length")
    val album = Album(
        amazonMusicId = "B01929HGH4",
        appleMusicId = "1441164426",
        artist = "Beatles",
        artistOrigin = "uk",
        deezerId = "12047952",
        genres = listOf("rock", "pop"),
        globalReviewsUrl = "https://1001albumsgenerator.com/albums/0ETFjACtuP2ADo6LFhL6HN/abbey-road",
        images = listOf(
            "https://i.scdn.co/image/ab67616d0000b273dc30583ba717007b00cceb25",
        ),
        name = "Abbey Road",
        qobuzId = "b5f135hx66fxa",
        releaseDate = "1969",
        slug = "abbey-road",
        spotifyId = "0ETFjACtuP2ADo6LFhL6HN",
        subGenres = listOf(
            "beatlesque",
            "british-invasion",
            "classic-rock",
            "merseybeat",
            "psychedelic-rock",
            "rock",
        ),
        tidalId = "55130630",
        uuid = "5f34ee8bf0857e55ed5ebdb4",
        wikipediaUrl = "https://en.wikipedia.org/wiki/Abbey_Road_(album)",
        youtubeMusicId = "OLAK5uy_lqcFZTOPHGwcnP0nYMzNuY0IES0fl7Fe4",
        summary =
            "Abbey Road is the eleventh studio album released by the English rock band the Beatles. It is the last album the group started recording, although Let It Be was the last album completed before the band's dissolution in April 1970. It was mostly recorded in April, July and August 1969, and was released on 26 September 1969 in the United Kingdom, and 1 October 1969 in the United States, reaching number one in both countries. A double A-side single from the album, \"Something\" / \"Come Together\" was released in October, which also topped the charts in the US.\n" +
                "Abbey Road incorporates genres such as blues, pop, and progressive rock and makes prominent use of the moog synthesizer and the Leslie speaker. It is also notable for having a long medley of songs on side two that have subsequently been covered as one suite by other notable artists. The album was recorded in a more collegial atmosphere than the Get Back / Let It Be sessions earlier in the year, but there were still significant confrontations within the band, particularly over Paul McCartney's song \"Maxwell's Silver Hammer\", and John Lennon did not perform on several tracks. By the time the album was released, Lennon had left the group, though this was not publicly announced until McCartney also quit the following year.\n" +
                "Although it was an immediate commercial success, it received mixed reviews. Some critics found its music inauthentic and criticized the production's artificial effects. By contrast, critics today view the album as one of the Beatles' best and rank it as one of the greatest albums of all time. George Harrison's two songs on the album, \"Something\" and \"Here Comes the Sun\", have been regarded as some of the best he wrote for the group. The album's cover, featuring the group walking across a zebra crossing outside Abbey Road Studios, has become one of the most famous and imitated in the history of recorded music.",
        type = Album.AlbumType.OFFICIAL,
    )

    val history = History(
        album = album,
        generatedAt = Instant.parse("2024-01-29T04:16:01.227Z"),
        globalRating = 4.46,
        rating = "5",
        revealedAlbum = true,
        review = "",
        index = 0,
    )

    val stats = AlbumStats(
        artist = "Beatles",
        averageRating = 4.46,
        controversialScore = 0.814428505365509,
        genres = listOf("rock", "pop"),
        name = "Abbey Road",
        votes = 24116,
        votesByGrade = AlbumStats.VotesByGrade(
            x1 = 78,
            x2 = 241,
            x3 = 1053,
            x4 = 3065,
            x5 = 7038,
        ),
    )

    val project = Project(
        currentAlbum = album,
        currentAlbumNotes = "",
        history = listOf(history),
        name = "Album Generator",
        paused = false,
        shareableUrl = "",
        updateFrequency = "dailyWithWeekends",
    )

    val genres = album.genres.map { LabelValuePair(it.capitalize(), it) }
}
