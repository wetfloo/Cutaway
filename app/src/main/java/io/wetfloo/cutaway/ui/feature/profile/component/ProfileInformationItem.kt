package io.wetfloo.cutaway.ui.feature.profile.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Link
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.wetfloo.cutaway.data.model.profile.ProfileInformationPiece
import io.wetfloo.cutaway.ui.component.SpacerSized

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
        val header: String
        val value: String
        val infoIcon: ImageVector
        val contentDescription: String?
        when (piece) {
            is ProfileInformationPiece.Formed -> {
                value = piece.value
                with(piece.type) {
                    header = stringResource(headerStringRes)
                    infoIcon = icon
                    contentDescription = iconDescription
                }
            }

            is ProfileInformationPiece.Link -> {
                value = piece.url
                header = piece.title
                infoIcon = Icons.Default.Link
                contentDescription = null
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = infoIcon,
                contentDescription = contentDescription,
            )

            SpacerSized(w = 4.dp)

            Text(
                text = header,
                style = MaterialTheme.typography.titleMedium,
            )
        }

        SpacerSized(h = 4.dp)

        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}
