package com.albumsgenerator.app.presentation.screens.history

import androidx.compose.ui.text.input.TextFieldValue
import com.albumsgenerator.app.domain.values.Rating

sealed interface HistoryScreenEvents {
    data class UpdateQuery(val query: TextFieldValue) : HistoryScreenEvents
    data class UpdateRating(val rating: Rating?) : HistoryScreenEvents
    data class UpdateGenre(val genre: String?) : HistoryScreenEvents
}
