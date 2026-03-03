package com.albumsgenerator.app.presentation.screens.top

import androidx.compose.runtime.Immutable
import com.albumsgenerator.app.domain.core.immutableListOf
import com.albumsgenerator.app.domain.core.immutableMapNotNull
import com.albumsgenerator.app.domain.models.AlbumStats
import com.albumsgenerator.app.domain.models.History
import com.albumsgenerator.app.domain.models.SpoilerMode
import com.albumsgenerator.app.presentation.utils.PreviewData
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class TopState(
    val histories: ImmutableList<History>,
    val stats: ImmutableList<AlbumStats>,
    val spoilerMode: SpoilerMode,
) {
    val items: ImmutableList<Pair<AlbumStats, History?>>
        get() = stats.immutableMapNotNull { stat ->
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
            histories = immutableListOf(PreviewData.history),
            stats = immutableListOf(PreviewData.stats),
            spoilerMode = SpoilerMode.VISIBLE,
        )

        val Pair<AlbumStats, History?>.key get() = first.name
    }
}
