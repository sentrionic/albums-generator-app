package com.albumsgenerator.app.presentation.screens.journey

import androidx.compose.runtime.Immutable
import com.albumsgenerator.app.domain.models.Album
import com.albumsgenerator.app.domain.models.History

@Immutable
data class JourneyState(
    val byDecade: List<Item>,
    val byGenre: List<Item>,
    val byOrigin: List<Item>,
    val byStyles: List<ItemWithAlbums>,
    val byYear: List<ItemWithAlbums>,
    val aboveAverageOutliers: List<History>,
    val belowAverageOutliers: List<History>,
) {
    data class Item(val label: String, val albumsCount: Int, val average: Float, val global: Float)

    data class ItemWithAlbums(val label: String, val average: Float, val albums: List<Album>)

    companion object {
        val EMPTY = JourneyState(
            byDecade = emptyList(),
            byGenre = emptyList(),
            byOrigin = emptyList(),
            byStyles = emptyList(),
            byYear = emptyList(),
            aboveAverageOutliers = emptyList(),
            belowAverageOutliers = emptyList(),
        )
    }
}
