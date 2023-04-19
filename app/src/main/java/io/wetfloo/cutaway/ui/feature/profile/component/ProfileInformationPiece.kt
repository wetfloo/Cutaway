package io.wetfloo.cutaway.ui.feature.profile.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import io.wetfloo.cutaway.ui.component.SpacerSized

@Composable
fun ProfileInformationItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    profileInformationPiece: ProfileInformationPiece,
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
            if (profileInformationPiece.icon != null) {
                val (imageVector, contentDescription) = profileInformationPiece.icon
                Icon(
                    imageVector = imageVector,
                    contentDescription = contentDescription,
                )
            }

            SpacerSized(w = 4.dp)

            Text(
                text = profileInformationPiece.header,
                style = MaterialTheme.typography.titleMedium,
            )
        }

        SpacerSized(h = 4.dp)

        Text(
            text = profileInformationPiece.value,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Immutable
data class ProfileInformationPiece(
    val header: String,
    val value: String,
    val icon: Icon? = null,
) {
    @Immutable
    data class Icon(
        val imageVector: ImageVector,
        val contentDescription: String? = null,
    )
}
