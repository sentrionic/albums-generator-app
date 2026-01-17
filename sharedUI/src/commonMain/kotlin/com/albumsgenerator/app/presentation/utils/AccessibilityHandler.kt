package com.albumsgenerator.app.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State

@Composable
expect fun collectIsScreenReaderEnabledAsState(): State<Boolean>
