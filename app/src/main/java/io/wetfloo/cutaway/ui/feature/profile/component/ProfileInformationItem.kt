package io.wetfloo.cutaway.ui.feature.profile.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.ui.component.SpacerSized
import io.wetfloo.cutaway.ui.feature.profile.state.ProfileInformationPiece

@Composable
fun ProfileInformationItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    piece: ProfileInformationPiece,
) {
    Column(
        modifier = modifier
            .clickable(
                indication = null,
                interactionSource = remember {
                    MutableInteractionSource()
                },
            ) {
                onClick()
            }
            .fillMaxWidth(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = piece.type?.icon ?: Icons.Default.Info,
                contentDescription = piece.type?.iconDescription,
            )

            SpacerSized(w = 4.dp)

            Text(
                text = stringResource(piece.type?.headerStringRes ?: R.string.piece_generic),
                style = MaterialTheme.typography.titleMedium,
            )
        }

        SpacerSized(h = 4.dp)

        Text(
            text = piece.value,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}
