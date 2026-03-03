package com.albumsgenerator.app.presentation.screens.journey

import androidx.compose.runtime.Immutable
import com.albumsgenerator.app.domain.core.emptyImmutableList
import com.albumsgenerator.app.domain.models.Album
import com.albumsgenerator.app.domain.models.History
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class JourneyState(
    val byDecade: ImmutableList<Item>,
    val byGenre: ImmutableList<Item>,
    val byOrigin: ImmutableList<Item>,
    val byStyles: ImmutableList<ItemWithAlbums>,
    val byYears: ImmutableList<ItemWithAlbums>,
    val aboveAverageOutliers: ImmutableList<History>,
    val belowAverageOutliers: ImmutableList<History>,
) {
    @Immutable
    data class Item(val label: String, val albumsCount: Int, val average: Float, val global: Float)

    @Immutable
    data class ItemWithAlbums(
        val label: String,
        val average: Float,
        val albums: ImmutableList<Album>,
    )

    companion object {
        val EMPTY = JourneyState(
            byDecade = emptyImmutableList(),
            byGenre = emptyImmutableList(),
            byOrigin = emptyImmutableList(),
            byStyles = emptyImmutableList(),
            byYears = emptyImmutableList(),
            aboveAverageOutliers = emptyImmutableList(),
            belowAverageOutliers = emptyImmutableList(),
        )
    }
}
