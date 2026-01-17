package com.albumsgenerator.app.presentation.utils

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ClipEntry

@OptIn(ExperimentalComposeUiApi::class)
actual fun clipEntryOf(string: String): ClipEntry = ClipEntry.withPlainText(string)
