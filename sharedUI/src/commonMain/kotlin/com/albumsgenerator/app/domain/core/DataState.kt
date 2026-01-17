package com.albumsgenerator.app.domain.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import org.jetbrains.compose.resources.StringResource

sealed interface DataState<T> {
    class Loading<T> : DataState<T>

    data class Success<T>(val content: T) : DataState<T>

    data class Error<T>(val message: StringResource) : DataState<T>

    fun contentOrNull(): T? = when (this) {
        is Success -> content
        else -> null
    }

    fun <R> mapSuccess(transform: (T) -> R): DataState<R> = when (this) {
        is Error -> Error(message)
        is Loading -> Loading()
        is Success -> Success(transform(content))
    }

    private val state get() = mapSuccess {}

    @Composable
    fun rememberLoadingState(): State<DataState<Unit>> = remember(state) {
        derivedStateOf { state }
    }
}
