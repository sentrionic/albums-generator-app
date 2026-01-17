package com.albumsgenerator.app.presentation.screens.history

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.albumsgenerator.app.datasources.repository.HistoryRepository
import com.albumsgenerator.app.domain.core.Coroutines
import com.albumsgenerator.app.domain.core.DataState
import com.albumsgenerator.app.domain.core.LabelValuePair
import com.albumsgenerator.app.domain.values.Rating
import com.albumsgenerator.app.presentation.utils.capitalize
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metrox.viewmodel.ViewModelAssistedFactory
import dev.zacsweers.metrox.viewmodel.ViewModelAssistedFactoryKey
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

@AssistedInject
class HistoryViewModel(
    @Assisted savedStateHandle: SavedStateHandle,
    historyRepository: HistoryRepository,
) : ViewModel() {
    private val rating = MutableStateFlow<Rating?>(null)
    private val genre = MutableStateFlow<String?>(null)

    @OptIn(SavedStateHandleSaveableApi::class)
    var query by savedStateHandle.saveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }

    private val queryFlow = snapshotFlow { query }

    val state = combine(
        historyRepository.historiesFlow(),
        genre,
        rating,
        queryFlow,
    ) { histories, genre, rating, query ->
        val filteredHistories = if (genre.isNullOrEmpty() && rating?.value.isNullOrEmpty() &&
            query.text.isBlank()
        ) {
            histories
        } else {
            histories.filter { history ->
                val album = history.album
                val matchesName = album.name.contains(query.text, true) ||
                    album.artist.contains(query.text, true)

                val matchesGenre = if (!genre.isNullOrEmpty()) {
                    genre in album.genres
                } else {
                    true
                }

                val matchesRating = if (!rating?.value.isNullOrEmpty()) {
                    rating.value == history.rating
                } else {
                    true
                }

                matchesName && matchesGenre && matchesRating
            }
        }

        DataState.Success(
            HistoryScreenState(
                filteredHistories = filteredHistories,
                genre = genre,
                rating = rating,
                historiesCount = histories.size,
                historiesWithRatingCount = histories.filter {
                    it.rating?.toIntOrNull() != null
                }.size,
                genres = histories
                    .flatMap { it.album.genres }
                    .distinct()
                    .map { LabelValuePair(label = it.capitalize(), value = it) },
            ),
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(Coroutines.SUBSCRIPTION_TIMEOUT_MS),
        initialValue = DataState.Loading(),
    )

    fun onEvent(event: HistoryScreenEvents) {
        when (event) {
            is HistoryScreenEvents.UpdateGenre -> genre.update { event.genre }
            is HistoryScreenEvents.UpdateRating -> rating.update { event.rating }
            is HistoryScreenEvents.UpdateQuery -> query = event.query
        }
    }

    @AssistedFactory
    @ViewModelAssistedFactoryKey(HistoryViewModel::class)
    @ContributesIntoMap(AppScope::class)
    @Suppress("unused")
    interface Factory : ViewModelAssistedFactory {
        override fun create(extras: CreationExtras): ViewModel =
            create(extras.createSavedStateHandle())

        fun create(@Assisted savedStateHandle: SavedStateHandle): HistoryViewModel
    }
}
