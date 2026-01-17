package com.albumsgenerator.app.presentation.utils

import android.content.ClipData
import androidx.compose.ui.platform.ClipEntry

actual fun clipEntryOf(string: String) = ClipEntry(ClipData.newPlainText("plain text", string))
