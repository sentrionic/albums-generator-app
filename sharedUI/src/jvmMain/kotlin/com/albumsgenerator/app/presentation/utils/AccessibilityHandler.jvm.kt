package com.albumsgenerator.app.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
actual fun collectIsScreenReaderEnabledAsState(): State<Boolean> {
    val screenReaderEnabled = remember {
        mutableStateOf(false)
    }

    return screenReaderEnabled
}
