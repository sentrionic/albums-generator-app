package com.albumsgenerator.app.presentation.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MusicNote
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Size

@Composable
fun NetworkImage(
    url: String?,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    icon: ImageVector = Icons.Outlined.MusicNote,
) {
    val context = LocalPlatformContext.current

    val placeholder: @Composable () -> Unit = {
        Box(
            modifier = modifier
                .background(MaterialTheme.colorScheme.surfaceContainerHighest),
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier
                    .size(75.dp)
                    .align(Alignment.Center),
            )
        }
    }

    if (url.isNullOrEmpty()) {
        placeholder()
    } else {
        SubcomposeAsyncImage(
            model = ImageRequest
                .Builder(context)
                .data(url)
                .size(Size.ORIGINAL)
                .crossfade(true)
                .build(),
            contentDescription = contentDescription,
            modifier = modifier,
            loading = { placeholder() },
            error = { placeholder() },
        )
    }
}
