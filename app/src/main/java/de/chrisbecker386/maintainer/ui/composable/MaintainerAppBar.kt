package de.chrisbecker386.maintainer.ui.composable

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.chrisbecker386.maintainer.R
import de.chrisbecker386.maintainer.ui.theme.MaintainerTheme

@Composable
fun MaintainerAppBar(
    title: String,
    showBackButton: Boolean = false,
    showContextMenu: Boolean = false,
    onBackButtonClick: () -> Unit = {},
    onContextMenuClick: () -> Unit = {},
    modifier: Modifier
) {
    TopAppBar(
        modifier = modifier.fillMaxWidth(),
        title = {
            Row(Modifier.fillMaxWidth(), Arrangement.Center) {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.onPrimary
                )
            }
        },
        backgroundColor = MaterialTheme.colors.primary,
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = onBackButtonClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.back)
                    )
                }
            }
        },
        actions = {
            if (showContextMenu) {
                IconButton(onClick = onContextMenuClick) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = stringResource(R.string.menu)
                    )
                }
            }
        }
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewMaintainerAppBar() {
    MaintainerTheme {
        MaintainerAppBar(
            "Preview",
            true,
            true,
            {},
            {},
            Modifier
                .height(40.dp)
                .fillMaxWidth()
        )
    }
}
