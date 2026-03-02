package com.albumsgenerator.app.presentation.screens.journey.components

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.your_journey_section_albums
import albumsgenerator.sharedui.generated.resources.your_journey_section_global
import albumsgenerator.sharedui.generated.resources.your_journey_section_your_average
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.albumsgenerator.app.presentation.common.modifiers.listSemantics
import com.albumsgenerator.app.presentation.ui.theme.AppTheme
import com.albumsgenerator.app.presentation.ui.theme.Paddings
import com.eygraber.compose.placeholder.material3.placeholder
import org.jetbrains.compose.resources.stringResource

@Composable
fun JourneySectionCard(
    title: String,
    label: String,
    listSize: Int,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .padding(all = Paddings.medium)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Paddings.large),
        ) {
            Text(
                text = title,
                modifier = Modifier
                    .semantics { heading() }
                    .placeholder(isLoading),
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.titleMedium,
            )

            Row(
                modifier = Modifier
                    .clearAndSetSemantics {},
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Paddings.small),
            ) {
                Text(
                    text = label.uppercase(),
                    modifier = Modifier
                        .weight(1f)
                        .placeholder(isLoading),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.bodySmall,
                )

                Text(
                    text = stringResource(Res.string.your_journey_section_albums),
                    modifier = Modifier
                        .weight(1f)
                        .placeholder(isLoading),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.bodySmall,
                )

                Text(
                    text = stringResource(Res.string.your_journey_section_your_average),
                    modifier = Modifier
                        .weight(1f)
                        .placeholder(isLoading),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.bodySmall,
                )

                Text(
                    text = stringResource(Res.string.your_journey_section_global),
                    modifier = Modifier
                        .weight(1f)
                        .placeholder(isLoading),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.bodySmall,
                )
            }

            Column(
                modifier = Modifier
                    .listSemantics(listSize),
                verticalArrangement = Arrangement.spacedBy(Paddings.small),
                content = content,
            )
        }
    }
}

@Preview
@Composable
private fun JourneySectionCardPreview() {
    AppTheme {
        JourneySectionCard(
            title = "By Section",
            label = "Section",
            listSize = 5,
        ) {}
    }
}
