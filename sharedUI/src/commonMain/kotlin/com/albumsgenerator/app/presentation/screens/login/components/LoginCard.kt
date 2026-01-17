package com.albumsgenerator.app.presentation.screens.login.components

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.book_title
import albumsgenerator.sharedui.generated.resources.login_authenticate
import albumsgenerator.sharedui.generated.resources.login_input_label
import albumsgenerator.sharedui.generated.resources.login_submit
import albumsgenerator.sharedui.generated.resources.login_subtitle
import albumsgenerator.sharedui.generated.resources.submitting_accessibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.albumsgenerator.app.presentation.ui.theme.AppTheme
import com.albumsgenerator.app.presentation.ui.theme.Paddings
import org.jetbrains.compose.resources.stringResource

@Composable
fun LoginCard(
    name: TextFieldValue,
    isSubmitting: Boolean,
    onUpdateName: (TextFieldValue) -> Unit,
    onAuthenticate: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val keyboard = LocalSoftwareKeyboardController.current

    Card(modifier = modifier) {
        Column(
            modifier = Modifier
                .padding(all = Paddings.large),
            verticalArrangement = Arrangement.spacedBy(space = Paddings.extraLarge),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(Res.string.book_title),
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium,
            )

            Text(
                text = stringResource(Res.string.login_subtitle),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
            )

            Text(
                text = stringResource(Res.string.login_authenticate),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
            )

            OutlinedTextField(
                value = name,
                onValueChange = onUpdateName,
                modifier = Modifier
                    .fillMaxWidth(),
                label = {
                    Text(
                        text = stringResource(Res.string.login_input_label),
                    )
                },
                shape = CircleShape,
            )

            val submittingDescription = stringResource(Res.string.submitting_accessibility)

            Button(
                onClick = {
                    keyboard?.hide()
                    onAuthenticate()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics {
                        if (isSubmitting) {
                            stateDescription = submittingDescription
                        }
                    },
                enabled = name.text.isNotBlank() && !isSubmitting,
                contentPadding = PaddingValues(all = Paddings.medium),
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(Res.string.login_submit),
                        modifier = Modifier
                            .align(Alignment.Center),
                        style = MaterialTheme.typography.titleMedium,
                    )

                    if (isSubmitting) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(20.dp)
                                .align(Alignment.CenterEnd),
                            color = ButtonDefaults.buttonColors().disabledContentColor,
                            strokeWidth = 3.dp,
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun LoginCardPreview() {
    AppTheme {
        LoginCard(
            name = TextFieldValue(),
            isSubmitting = false,
            onUpdateName = {},
            onAuthenticate = {},
        )
    }
}

@Preview
@Composable
private fun LoginCardSubmittingPreview() {
    AppTheme {
        LoginCard(
            name = TextFieldValue(),
            isSubmitting = true,
            onUpdateName = {},
            onAuthenticate = {},
        )
    }
}
