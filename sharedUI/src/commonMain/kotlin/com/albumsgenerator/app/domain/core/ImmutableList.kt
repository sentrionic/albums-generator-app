package com.albumsgenerator.app.domain.core

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

fun <T> emptyImmutableList(): ImmutableList<T> = emptyList<T>().toImmutableList()

inline fun <T, R> Iterable<T>.immutableMap(transform: (T) -> R): ImmutableList<R> =
    map(transform).toImmutableList()

inline fun <T, R : Any> Iterable<T>.immutableMapNotNull(transform: (T) -> R?): ImmutableList<R> =
    mapNotNull(transform).toImmutableList()

fun <T> ImmutableList<T>?.orEmpty(): ImmutableList<T> = this ?: emptyImmutableList()

inline fun <T, R : Comparable<R>> Iterable<T>.immutableSortedBy(
    crossinline selector: (T) -> R?,
): ImmutableList<T> = sortedWith(compareBy(selector)).toImmutableList()

inline fun <T, R : Comparable<R>> Iterable<T>.immutableSortedByDescending(
    crossinline selector: (T) -> R?,
): ImmutableList<T> = sortedWith(compareByDescending(selector)).toImmutableList()

inline fun <T> Iterable<T>.immutableFilter(predicate: (T) -> Boolean): ImmutableList<T> =
    filterTo(ArrayList(), predicate).toImmutableList()

fun <T> immutableListOf(vararg elements: T): ImmutableList<T> = elements.toList().toImmutableList()

fun String.immutableSplit(delimiter: String): ImmutableList<String> =
    split(delimiter).toImmutableList()
