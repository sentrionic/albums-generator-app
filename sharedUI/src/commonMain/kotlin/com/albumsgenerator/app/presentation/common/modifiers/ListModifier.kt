package com.albumsgenerator.app.presentation.common.modifiers

import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.CollectionInfo
import androidx.compose.ui.semantics.CollectionItemInfo
import androidx.compose.ui.semantics.collectionInfo
import androidx.compose.ui.semantics.collectionItemInfo
import androidx.compose.ui.semantics.semantics

fun Modifier.listSemantics(size: Int): Modifier = semantics {
    collectionInfo = CollectionInfo(rowCount = size, columnCount = 1)
}

fun Modifier.listItemSemantics(index: Int): Modifier = semantics(mergeDescendants = true) {
    collectionItemInfo = CollectionItemInfo(
        rowIndex = index,
        rowSpan = 1,
        columnIndex = 0,
        columnSpan = 1,
    )
}
