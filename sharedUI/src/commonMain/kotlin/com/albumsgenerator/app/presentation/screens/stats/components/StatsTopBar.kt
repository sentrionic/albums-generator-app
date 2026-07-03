package com.albumsgenerator.app.presentation.screens.stats.components

import albumsgenerator.sharedui.generated.resources.Res
import albumsgenerator.sharedui.generated.resources.ic_more_vert
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.albumsgenerator.app.domain.models.AlbumType
import com.albumsgenerator.app.presentation.common.components.DropdownItem
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun StatsTopBar(
    title: String,
    type: AlbumType,
    onChangeType: (AlbumType) -> Unit,
    modifier: Modifier = Modifier,
) {
    var showDialog by rememberSaveable {
        mutableStateOf(false)
    }

    TopAppBar(
        title = {
            Text(
                text = title,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        modifier = modifier,
        actions = {
            Box {
                IconButton(
                    onClick = {
                        showDialog = true
                    },
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_more_vert),
                        contentDescription = null,
                    )
                }

                DropdownMenu(
                    expanded = showDialog,
                    onDismissRequest = { showDialog = false },
                ) {
                    for (option in AlbumType.entries) {
                        DropdownItem(
                            label = stringResource(option.label),
                            onSelect = {
                                onChangeType(option)
                                showDialog = false
                            },
                            isItemCurrent = type == option,
                        )
                    }
                }
            }
        },
    )
}
