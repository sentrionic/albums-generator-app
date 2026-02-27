package com.albumsgenerator.app.presentation.screens.top

import androidx.compose.runtime.Immutable
import com.albumsgenerator.app.domain.models.AlbumStats
import com.albumsgenerator.app.domain.models.History
import com.albumsgenerator.app.domain.models.SpoilerMode
import com.albumsgenerator.app.presentation.utils.PreviewData

@Immutable
data class TopState(
    val histories: List<History>,
    val stats: List<AlbumStats>,
    val spoilerMode: SpoilerMode,
) {
    val items: List<Pair<AlbumStats, History?>>
        get() = stats.mapNotNull { stat ->
            val relatedHistory = histories.firstOrNull {
                it.album.name == stat.name &&
                    it.album.artist == stat.artist
            }

            if (relatedHistory == null && spoilerMode == SpoilerMode.HIDDEN) {
                null
            } else {
                stat to relatedHistory
            }
        }

    companion object {
        val EMPTY = TopState(
            histories = listOf(PreviewData.history),
            stats = listOf(PreviewData.stats),
            spoilerMode = SpoilerMode.VISIBLE,
        )

        val Pair<AlbumStats, History?>.key get() = first.name
    }
}
