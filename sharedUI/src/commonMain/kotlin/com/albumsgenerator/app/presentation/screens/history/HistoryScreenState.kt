package com.albumsgenerator.app.presentation.screens.history

import androidx.compose.runtime.Immutable
import com.albumsgenerator.app.domain.core.LabelValuePair
import com.albumsgenerator.app.domain.core.emptyImmutableList
import com.albumsgenerator.app.domain.models.History
import com.albumsgenerator.app.domain.values.Rating
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class HistoryScreenState(
    val filteredHistories: ImmutableList<History> = emptyImmutableList(),
    val genre: String? = null,
    val rating: Rating? = null,
    val historiesCount: Int = 0,
    val historiesWithRatingCount: Int = 0,
    val genres: ImmutableList<LabelValuePair> = emptyImmutableList(),
)
