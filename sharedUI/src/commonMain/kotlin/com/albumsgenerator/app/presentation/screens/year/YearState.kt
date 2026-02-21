package com.albumsgenerator.app.presentation.screens.year

import androidx.compose.runtime.Immutable
import com.albumsgenerator.app.domain.models.AlbumStats
import com.albumsgenerator.app.domain.models.History

@Immutable
data class YearState(
    val histories: List<History>,
    val stats: List<AlbumStats>,
    val spoilerFree: Boolean,
)
