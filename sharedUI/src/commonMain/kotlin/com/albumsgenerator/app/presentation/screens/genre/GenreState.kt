package com.albumsgenerator.app.presentation.screens.genre

import androidx.compose.runtime.Immutable
import com.albumsgenerator.app.domain.models.AlbumStats
import com.albumsgenerator.app.domain.models.History

@Immutable
data class GenreState(val histories: List<History>, val stats: List<AlbumStats>)
