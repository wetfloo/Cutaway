package io.wetfloo.cutaway.ui.feature.profile.component

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.core.common.forEachInBetween
import io.wetfloo.cutaway.data.model.profile.ProfileInformation
import io.wetfloo.cutaway.data.model.profile.ProfileInformationPiece
import io.wetfloo.cutaway.ui.component.DefaultDivider
import io.wetfloo.cutaway.ui.component.SpacerSized

@Composable
fun ProfileInformationTop(
    data: ProfileInformation,
    onCardClick: () -> Unit,
    onQrClick: () -> Unit,
    onEditClick: () -> Unit,
    onInfoCopied: () -> Unit,
    isDeleteVisible: Boolean,
    onDeleteClick: () -> Unit,
    onActivityLaunchFailed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    val informationPiecesPinned by remember {
        derivedStateOf {
            data
                .pieces
                .takeLast(2)
        }
    }

    Card(
        modifier = modifier
            .clickable(onClick = onCardClick),
    ) {
        Column(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.card_spacing_internal)),
        ) {
            Row {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .crossfade(true)
                        .data(data.pictureUrl)
                        .fallback(R.drawable.no_image_available)
                        .build(),
                    contentDescription = stringResource(R.string.profile_image_description),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(percent = 100)),
                )

                SpacerSized(w = 16.dp)

                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    Text(
                        text = data.fullName,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.headlineMedium,
                    )
                }
            }

            informationPiecesPinned.forEachInBetween(
                inBetweenBlock = {
                    DefaultDivider(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .sizeIn(maxWidth = 120.dp),
                    )
                }
            ) { piece ->
                ProfileInformationItem(
                    modifier = Modifier
                        .fillMaxWidth(),
                    piece = piece,
                    onClick = {
                        when (piece) {
                            is ProfileInformationPiece.Formed -> {
                                clipboardManager.setText(AnnotatedString(piece.value))
                                onInfoCopied()
                            }

                            is ProfileInformationPiece.Link -> {
                                try {
                                    val intent = Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse(piece.url),
                                    )
                                    context.startActivity(intent)
                                } catch (e: ActivityNotFoundException) {
                                    onActivityLaunchFailed()
                                } catch (e: SecurityException) {
                                    onActivityLaunchFailed()
                                }
                            }
                        }
                    },
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                IconButton(onClick = onQrClick) {
                    Icon(
                        imageVector = Icons.Default.QrCode2,
                        contentDescription = null,
                    )
                }

                IconButton(onClick = onEditClick) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                    )
                }

                if (isDeleteVisible) {
                    IconButton(onClick = onDeleteClick) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                        )
                    }
                }
            }
        }
    }
}
