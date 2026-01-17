package com.albumsgenerator.app.presentation.screens.login.components

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.ic_background
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.albumsgenerator.app.presentation.screens.login.LoginViewModel
import com.albumsgenerator.app.presentation.ui.theme.AppTheme
import com.albumsgenerator.app.presentation.ui.theme.Paddings
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginContent(
    name: TextFieldValue,
    state: LoginViewModel.State,
    onUpdateName: (TextFieldValue) -> Unit,
    onAuthenticate: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state) {
        val message = state.message
        if (!message.isNullOrEmpty()) {
            snackbarHostState.showSnackbar(message)
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
        ) {
            Image(
                painter = painterResource(Res.drawable.ic_background),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.FillBounds,
            )

            LoginCard(
                name = name,
                isSubmitting = state.isSubmitting,
                onUpdateName = onUpdateName,
                onAuthenticate = onAuthenticate,
                modifier = Modifier
                    .align(Alignment.Center)
                    .verticalScroll(rememberScrollState())
                    .padding(all = Paddings.large),
            )
        }
    }
}

@Preview
@Composable
private fun LoginContentPreview() {
    AppTheme {
        LoginContent(
            name = TextFieldValue(),
            state = LoginViewModel.State(),
            onUpdateName = {},
            onAuthenticate = {},
        )
    }
}
