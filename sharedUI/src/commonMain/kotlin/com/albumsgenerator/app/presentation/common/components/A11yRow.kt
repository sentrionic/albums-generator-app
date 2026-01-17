package com.albumsgenerator.app.presentation.common.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.layout.Measured
import androidx.compose.ui.platform.LocalDensity

@Suppress("ModifierReuse")
@Composable
fun A11yRow(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    content: @Composable (RowScope.() -> Unit),
) {
    val isAccessibilitySize = LocalDensity.current.fontScale > 1.5f

    if (isAccessibilitySize) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(horizontalArrangement.spacing),
            horizontalAlignment = horizontalAlignment,
        ) {
            A11yRowScope.content()
        }
    } else {
        Row(
            modifier = modifier,
            horizontalArrangement = horizontalArrangement,
            verticalAlignment = Alignment.CenterVertically,
            content = content,
        )
    }
}

private object A11yRowScope : RowScope {
    override fun Modifier.weight(
        weight: Float,
        fill: Boolean,
    ) = this

    override fun Modifier.align(alignment: Alignment.Vertical) = this

    override fun Modifier.alignBy(alignmentLine: HorizontalAlignmentLine) = this

    override fun Modifier.alignByBaseline() = this

    override fun Modifier.alignBy(alignmentLineBlock: (Measured) -> Int) = this
}
