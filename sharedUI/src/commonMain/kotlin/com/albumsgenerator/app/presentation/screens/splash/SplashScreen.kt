package com.albumsgenerator.app.presentation.screens.splash

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.ic_logo
import albumsgenerator.sharedui.generated.resources.syncing
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.albumsgenerator.app.presentation.ui.theme.AppTheme
import com.albumsgenerator.app.presentation.ui.theme.DarkColorScheme
import com.albumsgenerator.app.presentation.ui.theme.Paddings
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

val logoPainter @Composable get() = painterResource(Res.drawable.ic_logo)

@Composable
fun SplashScreen(modifier: Modifier = Modifier) {
    Scaffold(modifier = modifier) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .width(IntrinsicSize.Min)
                    .semantics(mergeDescendants = true) {},
            ) {
                Image(
                    painter = logoPainter,
                    contentDescription = null,
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(
                        space = Paddings.large,
                        alignment = Alignment.CenterHorizontally,
                    ),
                ) {
                    Text(
                        text = stringResource(Res.string.syncing),
                        style = MaterialTheme.typography.titleMedium,
                    )
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(20.dp),
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun SplashScreenPreview() {
    AppTheme {
        SplashScreen()
    }
}

@Preview
@Composable
private fun SplashScreenDarkPreview() {
    AppTheme(colorScheme = DarkColorScheme) {
        SplashScreen()
    }
}
