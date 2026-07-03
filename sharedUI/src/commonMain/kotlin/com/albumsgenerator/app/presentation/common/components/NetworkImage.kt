package com.albumsgenerator.app.presentation.common.components

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.ic_music_off
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Size
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun NetworkImage(
    url: String?,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    icon: DrawableResource = Res.drawable.ic_music_off,
) {
    val context = LocalPlatformContext.current

    val placeholder: @Composable () -> Unit = {
        Box(
            modifier = modifier
                .background(MaterialTheme.colorScheme.surfaceContainerHighest),
        ) {
            Icon(
                painter = painterResource(icon),
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
