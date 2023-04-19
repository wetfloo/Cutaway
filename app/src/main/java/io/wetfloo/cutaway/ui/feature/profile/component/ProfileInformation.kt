package io.wetfloo.cutaway.ui.feature.profile.component

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.ui.component.SpacerSized

@Composable
fun ProfileInformation(
    imageData: Any?,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    Card(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp),
        ) {
            Row {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .crossfade(true)
                        .data(imageData)
                        .build(),
                    contentDescription = stringResource(R.string.profile_image_description),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .sizeIn(
                            maxWidth = 100.dp,
                            maxHeight = 100.dp,
                        )
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(percent = 100)),
                )

                SpacerSized(w = 16.dp)

                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    Text(
                        text = "name",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.headlineMedium,
                    )

                    SpacerSized(h = 4.dp)

                    Text(
                        text = "status",
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }

            SpacerSized(h = DIVIDER_SPACING.dp)

            Divider(
                modifier = Modifier
                    .padding(
                        horizontal = 8.dp,
                    ),
                color = MaterialTheme
                    .colorScheme
                    .onSurfaceVariant
                    .copy(
                        alpha = .15f,
                    ),
            )

            SpacerSized(h = DIVIDER_SPACING.dp)

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                ProfileInformationItem(
                    profileInformationPiece = ProfileInformationPiece(
                        header = "header",
                        value = "value",
                        icon = ProfileInformationPiece.Icon(
                            imageVector = Icons.Default.Work,
                            contentDescription = null, // TODO
                        )
                    ),
                    onClick = {
                        Toast.makeText(
                            context,
                            "Profile info piece clicked",
                            Toast.LENGTH_SHORT,
                        ).show()
                    },
                )
            }
        }
    }
}

private const val DIVIDER_SPACING = 16
