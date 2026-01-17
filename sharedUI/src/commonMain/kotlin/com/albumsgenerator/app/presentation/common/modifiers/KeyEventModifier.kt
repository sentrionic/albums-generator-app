package com.albumsgenerator.app.presentation.common.modifiers

import androidx.compose.ui.Modifier

expect fun Modifier.onEscape(onEscape: () -> Unit): Modifier
