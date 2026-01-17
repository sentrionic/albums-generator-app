package com.albumsgenerator.app.presentation.screens.history

import androidx.compose.runtime.Immutable
import com.albumsgenerator.app.domain.core.LabelValuePair
import com.albumsgenerator.app.domain.models.History
import com.albumsgenerator.app.domain.values.Rating

@Immutable
data class HistoryScreenState(
    val filteredHistories: List<History> = emptyList(),
    val genre: String? = null,
    val rating: Rating? = null,
    val historiesCount: Int = 0,
    val historiesWithRatingCount: Int = 0,
    val genres: List<LabelValuePair> = emptyList(),
)
