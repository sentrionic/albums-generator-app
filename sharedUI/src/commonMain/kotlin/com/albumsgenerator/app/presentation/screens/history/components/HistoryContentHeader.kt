package com.albumsgenerator.app.presentation.screens.history.components

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.history_filter_all_genres
import albumsgenerator.sharedui.generated.resources.history_filter_all_ratings
import albumsgenerator.sharedui.generated.resources.history_filter_rating_unrated
import albumsgenerator.sharedui.generated.resources.ic_arrow_forward
import albumsgenerator.sharedui.generated.resources.star_rating
import albumsgenerator.sharedui.generated.resources.your_journey
import albumsgenerator.sharedui.generated.resources.your_journey_navigate_accessibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import com.albumsgenerator.app.domain.core.LabelValuePair
import com.albumsgenerator.app.domain.core.emptyImmutableList
import com.albumsgenerator.app.domain.core.immutableListOf
import com.albumsgenerator.app.domain.models.History
import com.albumsgenerator.app.domain.values.Rating
import com.albumsgenerator.app.presentation.common.components.A11yRow
import com.albumsgenerator.app.presentation.common.components.DropdownMenu
import com.albumsgenerator.app.presentation.common.components.SearchField
import com.albumsgenerator.app.presentation.screens.history.HistoryScreenEvents
import com.albumsgenerator.app.presentation.ui.theme.AppTheme
import com.albumsgenerator.app.presentation.ui.theme.Paddings
import com.eygraber.compose.placeholder.material3.placeholder
import kotlinx.collections.immutable.ImmutableList
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun HistoryContentHeader(
    query: TextFieldState,
    rating: Rating?,
    genres: ImmutableList<LabelValuePair>,
    genre: String?,
    sendEvent: (HistoryScreenEvents) -> Unit,
    navigateToJourney: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Paddings.medium),
    ) {
        YourJourneyCard(
            modifier = Modifier
                .clip(MaterialTheme.shapes.large)
                .clickable(
                    enabled = !isLoading,
                    onClickLabel = stringResource(Res.string.your_journey_navigate_accessibility),
                    role = Role.Button,
                    onClick = navigateToJourney,
                )
                .placeholder(visible = isLoading),
        )

        SearchField(
            query = query.text.toString(),
            setQuery = { sendEvent(HistoryScreenEvents.UpdateQuery(it)) },
            enabled = !isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .placeholder(visible = isLoading),
        )

        FilterMenus(
            rating = rating,
            genres = genres,
            genre = genre,
            sendEvent = sendEvent,
            isLoading = isLoading,
        )
    }
}

@Composable
private fun YourJourneyCard(modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Row(
            modifier = Modifier
                .padding(all = Paddings.medium)
                .fillMaxWidth(),
        ) {
            Text(
                text = stringResource(Res.string.your_journey),
                modifier = Modifier
                    .weight(1f),
            )
            Icon(
                painter = painterResource(Res.drawable.ic_arrow_forward),
                contentDescription = null,
            )
        }
    }
}

@Composable
private fun FilterMenus(
    rating: Rating?,
    genres: ImmutableList<LabelValuePair>,
    genre: String?,
    sendEvent: (HistoryScreenEvents) -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
) {
    A11yRow(
        modifier = modifier,
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

        RatingMenu(
            rating = rating,
            onUpdateRating = {
                sendEvent(HistoryScreenEvents.UpdateRating(it))
            },
            modifier = Modifier
                .weight(1f)
                .placeholder(visible = isLoading),
            isLoading = isLoading,
        )
    }
}

@Composable
private fun RatingMenu(
    rating: Rating?,
    onUpdateRating: (Rating?) -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
) {
    val fiveString = pluralStringResource(Res.plurals.star_rating, 5, 5)
    val fourString = pluralStringResource(Res.plurals.star_rating, 4, 4)
    val threeString = pluralStringResource(Res.plurals.star_rating, 3, 3)
    val twoString = pluralStringResource(Res.plurals.star_rating, 2, 2)
    val oneString = pluralStringResource(Res.plurals.star_rating, 1, 1)
    val unratedString = stringResource(Res.string.history_filter_rating_unrated)

    val ratingOptions = remember {
        immutableListOf(
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
            onUpdateRating(Rating(it.value))
        },
        onReset = {
            onUpdateRating(null)
        },
        modifier = modifier,
        formatItem = { it.label },
        isItemCurrent = { it.value == rating?.value },
        enabled = !isLoading,
    )
}

@Preview(showBackground = true)
@Composable
private fun HistoryContentHeaderPreview() {
    AppTheme {
        HistoryContentHeader(
            query = TextFieldState(),
            rating = null,
            genres = emptyImmutableList(),
            genre = null,
            sendEvent = {},
            navigateToJourney = {},
            modifier = Modifier
                .padding(all = Paddings.medium),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HistoryContentHeaderLoadingPreview() {
    AppTheme {
        HistoryContentHeader(
            query = TextFieldState(),
            rating = null,
            genres = emptyImmutableList(),
            genre = null,
            sendEvent = {},
            navigateToJourney = {},
            modifier = Modifier
                .padding(all = Paddings.medium),
            isLoading = true,
        )
    }
}
