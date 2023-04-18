package io.wetfloo.cutaway.ui.feature.profile.component

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.core.ui.compose.core.bigPictureShape

@Composable
fun ProfileImage(
    imageData: Any?,
    modifier: Modifier = Modifier,
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .crossfade(true)
            .data(imageData)
            .build(),
        contentDescription = stringResource(R.string.profile_image_description),
        contentScale = ContentScale.Crop,
        modifier = modifier
            .sizeIn(
                maxWidth = 400.dp,
                maxHeight = 400.dp,
            )
            .aspectRatio(1f)
            .clip(bigPictureShape),
    )
}
