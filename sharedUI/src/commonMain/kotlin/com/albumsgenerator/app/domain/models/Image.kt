package com.albumsgenerator.app.domain.models

import androidx.compose.runtime.Immutable

@Immutable
data class Image(val height: Int, val url: String, val width: Int)
