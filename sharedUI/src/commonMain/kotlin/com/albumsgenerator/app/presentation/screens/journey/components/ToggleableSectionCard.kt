package com.albumsgenerator.app.presentation.screens.journey.components

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.accordion_collapse_accesibility
import albumsgenerator.sharedui.generated.resources.accordion_expand_accesibility
import albumsgenerator.sharedui.generated.resources.ic_arrow_drop_down
import albumsgenerator.sharedui.generated.resources.ic_arrow_drop_up
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.collapse
import androidx.compose.ui.semantics.expand
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.albumsgenerator.app.presentation.common.modifiers.listSemantics
import com.albumsgenerator.app.presentation.ui.theme.AppTheme
import com.albumsgenerator.app.presentation.ui.theme.Paddings
import com.albumsgenerator.app.presentation.utils.format
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ToggleableSectionCard(
    title: String,
    average: Float,
    albumsCount: Int,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    var showSection by rememberSaveable { mutableStateOf(false) }

    val onClickLabel = stringResource(
        if (showSection) {
            Res.string.accordion_collapse_accesibility
        } else {
            Res.string.accordion_expand_accesibility
        },
    )

    Card(
        modifier = modifier,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(Paddings.large),
        ) {
            Row(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.large)
                    .clickable(
                        onClickLabel = onClickLabel,
                        onClick = { showSection = !showSection },
                    )
                    .fillMaxWidth()
                    .padding(all = Paddings.medium)
                    .semantics(mergeDescendants = true) {
                        heading()
                        if (showSection) {
                            collapse {
                                showSection = false
                                true
                            }
                        } else {
                            expand {
                                showSection = true
                                true
                            }
                        }
                    },
                horizontalArrangement = Arrangement.spacedBy(Paddings.small),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "$title: ${average.format()} ($albumsCount)",
                    modifier = Modifier
                        .weight(1f),
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.titleMedium,
                )

                Icon(
                    painter = painterResource(
                        if (showSection) {
                            Res.drawable.ic_arrow_drop_up
                        } else {
                            Res.drawable.ic_arrow_drop_down
                        },
                    ),
                    contentDescription = null,
                )
            }

            AnimatedVisibility(
                visible = showSection,
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = Paddings.medium)
                        .padding(bottom = Paddings.medium)
                        .fillMaxWidth()
                        .listSemantics(albumsCount),
                    verticalArrangement = Arrangement.spacedBy(Paddings.small),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    content = content,
                )
            }
        }
    }
}

@Preview
@Composable
private fun ToggleableSectionCardPreview() {
    AppTheme {
        ToggleableSectionCard(
            title = "Section",
            average = 0.0f,
            albumsCount = 0,
        ) {}
    }
}
