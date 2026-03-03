package com.albumsgenerator.app.presentation.screens.journey.components

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.your_journey_tab_journey
import albumsgenerator.sharedui.generated.resources.your_journey_tab_styles
import albumsgenerator.sharedui.generated.resources.your_journey_tab_year
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.albumsgenerator.app.presentation.screens.journey.JourneyState
import com.albumsgenerator.app.presentation.ui.theme.AppTheme
import com.albumsgenerator.app.presentation.ui.theme.Paddings
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

private enum class Destination(val label: StringResource) {
    JOURNEY(label = Res.string.your_journey_tab_journey),
    STYLES(label = Res.string.your_journey_tab_styles),
    YEAR(label = Res.string.your_journey_tab_year),
}

@Composable
fun JourneyContent(
    state: JourneyState,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
) {
    var selectedDestination by rememberSaveable {
        mutableIntStateOf(Destination.JOURNEY.ordinal)
    }

    Column(modifier = modifier) {
        PrimaryTabRow(selectedTabIndex = selectedDestination) {
            Destination.entries.forEachIndexed { index, destination ->
                Tab(
                    selected = selectedDestination == index,
                    onClick = {
                        selectedDestination = index
                    },
                    enabled = !isLoading,
                    text = {
                        Text(
                            text = stringResource(destination.label),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                    },
                )
            }
        }

        when (selectedDestination) {
            Destination.JOURNEY.ordinal -> {
                JourneyTab(
                    state = state,
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(all = Paddings.medium),
                    isLoading = isLoading,
                )
            }

            Destination.STYLES.ordinal -> {
                StylesTab(
                    byStyles = state.byStyles,
                )
            }

            Destination.YEAR.ordinal -> {
                YearByYearTab(
                    byYears = state.byYears,
                )
            }
        }
    }
}

@Preview
@Composable
private fun JourneyContentPreview() {
    AppTheme {
        JourneyContent(
            state = JourneyState.EMPTY,
        )
    }
}
