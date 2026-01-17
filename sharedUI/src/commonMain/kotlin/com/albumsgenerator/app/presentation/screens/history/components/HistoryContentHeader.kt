package com.albumsgenerator.app.presentation.screens.history.components

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.history_filter_all_genres
import albumsgenerator.sharedui.generated.resources.history_filter_all_ratings
import albumsgenerator.sharedui.generated.resources.history_filter_rating_unrated
import albumsgenerator.sharedui.generated.resources.history_filter_search
import albumsgenerator.sharedui.generated.resources.star_rating
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.albumsgenerator.app.domain.core.LabelValuePair
import com.albumsgenerator.app.domain.models.History
import com.albumsgenerator.app.domain.values.Rating
import com.albumsgenerator.app.presentation.common.components.A11yRow
import com.albumsgenerator.app.presentation.common.components.DropdownMenu
import com.albumsgenerator.app.presentation.screens.history.HistoryScreenEvents
import com.albumsgenerator.app.presentation.ui.theme.AppTheme
import com.albumsgenerator.app.presentation.ui.theme.Paddings
import com.eygraber.compose.placeholder.material3.placeholder
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun HistoryContentHeader(
    query: TextFieldValue,
    rating: Rating?,
    genres: List<LabelValuePair>,
    genre: String?,
    sendEvent: (HistoryScreenEvents) -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Paddings.medium),
    ) {
        SearchField(
            query = query,
            setQuery = { sendEvent(HistoryScreenEvents.UpdateQuery(it)) },
            enabled = !isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .placeholder(visible = isLoading),
        )

        A11yRow(
            horizontalArrangement = Arrangement.spacedBy(Paddings.medium),
        ) {
            DropdownMenu(
                label = stringResource(Res.string.history_filter_all_genres),
                items = genres,
                onSelect = {
                    sendEvent(HistoryScreenEvents.UpdateGenre(it.value))
                },
                onReset = {
                    sendEvent(HistoryScreenEvents.UpdateGenre(null))
                },
                modifier = Modifier
                    .weight(1f)
                    .placeholder(visible = isLoading),
                formatItem = { it.label },
                isItemCurrent = { it.value == genre },
                enabled = !isLoading,
            )

            val fiveString = pluralStringResource(Res.plurals.star_rating, 5, 5)
            val fourString = pluralStringResource(Res.plurals.star_rating, 4, 4)
            val threeString = pluralStringResource(Res.plurals.star_rating, 3, 3)
            val twoString = pluralStringResource(Res.plurals.star_rating, 2, 2)
            val oneString = pluralStringResource(Res.plurals.star_rating, 1, 1)
            val unratedString = stringResource(Res.string.history_filter_rating_unrated)

            val ratingOptions = remember {
                listOf(
                    LabelValuePair(
                        label = fiveString,
                        value = "5",
                    ),
                    LabelValuePair(
                        label = fourString,
                        value = "4",
                    ),
                    LabelValuePair(
                        label = threeString,
                        value = "3",
                    ),
                    LabelValuePair(
                        label = twoString,
                        value = "2",
                    ),
                    LabelValuePair(
                        label = oneString,
                        value = "1",
                    ),
                    LabelValuePair(
                        label = unratedString,
                        value = History.SKIPPED_TAG,
                    ),
                )
            }

            DropdownMenu(
                label = stringResource(Res.string.history_filter_all_ratings),
                items = ratingOptions,
                onSelect = {
                    sendEvent(HistoryScreenEvents.UpdateRating(Rating(it.value)))
                },
                onReset = {
                    sendEvent(HistoryScreenEvents.UpdateRating(null))
                },
                modifier = Modifier
                    .weight(1f)
                    .placeholder(visible = isLoading),
                formatItem = { it.label },
                isItemCurrent = { it.value == rating?.value },
                enabled = !isLoading,
            )
        }
    }
}

@Composable
private fun SearchField(
    query: TextFieldValue,
    setQuery: (TextFieldValue) -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier,
) {
    val keyboard = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = query,
        onValueChange = setQuery,
        modifier = modifier,
        enabled = enabled,
        placeholder = {
            Text(text = stringResource(Res.string.history_filter_search))
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = null,
            )
        },
        trailingIcon = if (query.text.isEmpty()) {
            null
        } else {
            {
                IconButton(
                    onClick = {
                        setQuery(TextFieldValue())
                        keyboard?.hide()
                    },
                ) {
                    Icon(
                        imageVector = Icons.Filled.Cancel,
                        contentDescription = null,
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboard?.hide()
            },
        ),
        shape = CircleShape,
        colors = OutlinedTextFieldDefaults.colors().copy(
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
        ),
    )
}

@Preview(showBackground = true)
@Composable
private fun HistoryContentHeaderPreview() {
    AppTheme {
        HistoryContentHeader(
            query = TextFieldValue(),
            rating = null,
            genres = emptyList(),
            genre = null,
            sendEvent = {},
            modifier = Modifier
                .padding(all = Paddings.medium),
        )
    }
}
